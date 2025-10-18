#!/bin/bash

# Script de configuração inicial do ambiente DevOps
# Uso: ./scripts/setup.sh [environment]

set -e

ENVIRONMENT=${1:-development}
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

echo "🚀 Configurando ambiente DevOps para Fintech App"
echo "📁 Diretório do projeto: $PROJECT_ROOT"
echo "🌍 Ambiente: $ENVIRONMENT"

# Função para verificar se um comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Verificar dependências
echo "🔍 Verificando dependências..."

if ! command_exists docker; then
    echo "❌ Docker não encontrado. Instale o Docker primeiro."
    exit 1
fi

if ! command_exists docker-compose; then
    echo "❌ Docker Compose não encontrado. Instale o Docker Compose primeiro."
    exit 1
fi

if ! command_exists java; then
    echo "❌ Java não encontrado. Instale o Java 17 primeiro."
    exit 1
fi

if ! command_exists mvn; then
    echo "❌ Maven não encontrado. Instale o Maven primeiro."
    exit 1
fi

echo "✅ Todas as dependências estão instaladas"

# Criar diretórios necessários
echo "📁 Criando diretórios..."
mkdir -p "$PROJECT_ROOT/logs/development"
mkdir -p "$PROJECT_ROOT/logs/staging"
mkdir -p "$PROJECT_ROOT/logs/production"
mkdir -p "$PROJECT_ROOT/nginx/ssl"

# Configurar permissões
echo "🔐 Configurando permissões..."
chmod +x "$PROJECT_ROOT/docker-entrypoint.sh"

# Copiar arquivo de ambiente
echo "⚙️ Configurando variáveis de ambiente..."
if [ -f "$PROJECT_ROOT/env.$ENVIRONMENT" ]; then
    cp "$PROJECT_ROOT/env.$ENVIRONMENT" "$PROJECT_ROOT/.env"
    echo "✅ Arquivo .env criado para ambiente $ENVIRONMENT"
else
    echo "⚠️ Arquivo env.$ENVIRONMENT não encontrado, usando configurações padrão"
fi

# Build da aplicação
echo "🔨 Compilando aplicação..."
cd "$PROJECT_ROOT"
mvn clean compile

# Executar testes
echo "🧪 Executando testes..."
mvn test

# Build da imagem Docker
echo "🐳 Construindo imagem Docker..."
docker build -t fintech-app:$ENVIRONMENT .

# Iniciar serviços
echo "🚀 Iniciando serviços para ambiente $ENVIRONMENT..."

case $ENVIRONMENT in
    "development")
        docker-compose up -d
        ;;
    "staging")
        docker-compose -f docker-compose.yml -f docker-compose.staging.yml up -d
        ;;
    "production")
        docker-compose -f docker-compose.yml -f docker-compose.production.yml up -d
        ;;
    *)
        echo "❌ Ambiente inválido: $ENVIRONMENT"
        echo "Ambientes válidos: development, staging, production"
        exit 1
        ;;
esac

# Aguardar serviços iniciarem
echo "⏳ Aguardando serviços iniciarem..."
sleep 10

# Verificar saúde dos serviços
echo "🏥 Verificando saúde dos serviços..."

# Verificar aplicação
if curl -f http://localhost:8080/health >/dev/null 2>&1; then
    echo "✅ Aplicação está saudável"
else
    echo "⚠️ Aplicação pode não estar respondendo corretamente"
fi

# Verificar banco de dados
if docker exec fintech-postgres pg_isready -U fintech_user >/dev/null 2>&1; then
    echo "✅ Banco de dados está saudável"
else
    echo "⚠️ Banco de dados pode não estar respondendo corretamente"
fi

# Verificar Redis
if docker exec fintech-redis redis-cli ping >/dev/null 2>&1; then
    echo "✅ Redis está saudável"
else
    echo "⚠️ Redis pode não estar respondendo corretamente"
fi

echo ""
echo "🎉 Configuração concluída!"
echo ""
echo "📊 Status dos serviços:"
docker-compose ps

echo ""
echo "🔗 URLs importantes:"
echo "  - Aplicação: http://localhost:8080"
echo "  - Health Check: http://localhost:8080/health"
echo "  - Nginx: http://localhost:80"

echo ""
echo "📝 Comandos úteis:"
echo "  - Ver logs: docker-compose logs -f"
echo "  - Parar serviços: docker-compose down"
echo "  - Executar testes: mvn test"
echo "  - Build: mvn clean package"

echo ""
echo "📚 Documentação: cat DEVOPS.md"
