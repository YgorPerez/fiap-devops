-- Script de inicialização do banco de dados Fintech
-- Este script é executado automaticamente quando o container PostgreSQL é criado

-- Criar extensões necessárias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criar tabela de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(20),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de contas
CREATE TABLE IF NOT EXISTS contas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    numero_conta INTEGER UNIQUE NOT NULL,
    saldo DECIMAL(15,2) DEFAULT 0.00,
    cliente_id UUID REFERENCES clientes(id),
    tipo_conta VARCHAR(20) NOT NULL CHECK (tipo_conta IN ('CORRENTE', 'POUPANCA')),
    limite DECIMAL(15,2) DEFAULT 0.00,
    taxa_juros DECIMAL(5,4) DEFAULT 0.0000,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de transações
CREATE TABLE IF NOT EXISTS transacoes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    conta_id UUID REFERENCES contas(id),
    tipo_transacao VARCHAR(20) NOT NULL CHECK (tipo_transacao IN ('DEPOSITO', 'SAQUE', 'TRANSFERENCIA')),
    valor DECIMAL(15,2) NOT NULL,
    descricao TEXT,
    data_transacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar índices para melhor performance
CREATE INDEX IF NOT EXISTS idx_clientes_cpf ON clientes(cpf);
CREATE INDEX IF NOT EXISTS idx_contas_numero ON contas(numero_conta);
CREATE INDEX IF NOT EXISTS idx_contas_cliente ON contas(cliente_id);
CREATE INDEX IF NOT EXISTS idx_transacoes_conta ON transacoes(conta_id);
CREATE INDEX IF NOT EXISTS idx_transacoes_data ON transacoes(data_transacao);

-- Inserir dados de exemplo para desenvolvimento
INSERT INTO clientes (nome, cpf, email, telefone) VALUES
('João Silva', '123.456.789-00', 'joao.silva@email.com', '(11) 99999-9999'),
('Maria Santos', '987.654.321-00', 'maria.santos@email.com', '(11) 88888-8888'),
('Pedro Oliveira', '456.789.123-00', 'pedro.oliveira@email.com', '(11) 77777-7777')
ON CONFLICT (cpf) DO NOTHING;

-- Inserir contas de exemplo
INSERT INTO contas (numero_conta, saldo, cliente_id, tipo_conta, limite, taxa_juros) VALUES
(2002, 500.00, (SELECT id FROM clientes WHERE cpf = '123.456.789-00'), 'CORRENTE', 1000.00, 0.0000),
(3003, 1000.00, (SELECT id FROM clientes WHERE cpf = '987.654.321-00'), 'POUPANCA', 0.00, 0.0200),
(4004, 2500.00, (SELECT id FROM clientes WHERE cpf = '456.789.123-00'), 'CORRENTE', 2000.00, 0.0000)
ON CONFLICT (numero_conta) DO NOTHING;

-- Criar função para atualizar timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.data_atualizacao = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Criar triggers para atualizar timestamp automaticamente
CREATE TRIGGER update_clientes_updated_at BEFORE UPDATE ON clientes
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_contas_updated_at BEFORE UPDATE ON contas
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
