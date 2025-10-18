# Pipeline DevOps - Aplicação Fintech

Este documento descreve a implementação completa de práticas DevOps para a aplicação Java Fintech, incluindo CI/CD, containerização e orquestração.

## 📋 Visão Geral

O pipeline implementado inclui:
- **CI/CD**: Integração e deploy contínuos com GitHub Actions
- **Containerização**: Docker com multi-stage build
- **Orquestração**: Docker Compose para diferentes ambientes
- **Testes**: Testes unitários automatizados com JUnit 5
- **Segurança**: Análise de vulnerabilidades e código
- **Monitoramento**: Health checks e logs estruturados

## 🏗️ Arquitetura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Development   │    │     Staging     │    │   Production    │
│                 │    │                 │    │                 │
│  docker-compose │───▶│ docker-compose  │───▶│ docker-compose  │
│                 │    │  + staging.yml  │    │ + production.yml│
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                    GitHub Actions CI/CD                        │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐│
│  │   Build &   │ │   Security  │ │   Deploy    │ │  Monitoring ││
│  │   Test      │ │    Scan     │ │   Pipeline  │ │   & Alerts  ││
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘│
└─────────────────────────────────────────────────────────────────┘
```

## 🚀 Pipeline CI/CD

### Workflows Implementados

#### 1. CI/CD Principal (`.github/workflows/ci-cd.yml`)

**Triggers:**
- Push para branches `main` e `develop`
- Pull requests para `main`

**Jobs:**
- **build-and-test**: Compilação, testes unitários e cobertura
- **code-quality**: Análise de código com SpotBugs e PMD
- **build-docker**: Build e push da imagem Docker
- **deploy-staging**: Deploy automático para staging (branch develop)
- **deploy-production**: Deploy automático para produção (branch main)
- **rollback**: Rollback manual via workflow_dispatch
- **cleanup**: Limpeza de recursos

#### 2. Security Scan (`.github/workflows/security.yml`)

**Triggers:**
- Push para branches principais
- Pull requests
- Agendado (toda segunda-feira às 2h)

**Jobs:**
- **dependency-check**: Análise OWASP de vulnerabilidades
- **docker-security**: Scan de segurança da imagem Docker
- **codeql-analysis**: Análise de código com CodeQL
- **secret-scan**: Detecção de secrets com TruffleHog

### Ambientes

#### Development
```bash
# Usar configurações padrão
docker-compose up -d
```

#### Staging
```bash
# Usar override para staging
docker-compose -f docker-compose.yml -f docker-compose.staging.yml up -d
```

#### Production
```bash
# Usar override para produção
docker-compose -f docker-compose.yml -f docker-compose.production.yml up -d
```

## 🐳 Containerização

### Dockerfile

**Características:**
- Multi-stage build para otimização
- Base: OpenJDK 17
- Usuário não-root para segurança
- Health checks integrados
- Variáveis de ambiente configuráveis

**Build:**
```bash
docker build -t fintech-app:latest .
```

### Docker Compose

**Serviços:**
- **fintech-app**: Aplicação Java principal
- **postgres**: Banco de dados PostgreSQL
- **redis**: Cache Redis
- **nginx**: Proxy reverso

**Recursos:**
- Volumes persistentes
- Redes isoladas
- Health checks
- Restart policies
- Resource limits (produção)

## 🧪 Testes

### Estrutura de Testes

```
src/test/java/classes/
├── ClienteTest.java
├── ContaTest.java
├── ContaCorrenteTest.java
└── ContaPoupancaTest.java
```

### Execução

```bash
# Testes unitários
mvn test

# Testes com cobertura
mvn jacoco:report

# Testes de integração
mvn verify -P integration-tests
```

### Cobertura

- **Meta**: 80% de cobertura de instruções
- **Relatórios**: Gerados em `target/site/jacoco/`
- **Upload**: Automático para Codecov

## 🔒 Segurança

### Análises Implementadas

1. **OWASP Dependency Check**
   - Scan de vulnerabilidades em dependências
   - Relatórios em HTML

2. **Trivy Security Scanner**
   - Análise de vulnerabilidades em imagens Docker
   - Integração com GitHub Security

3. **CodeQL**
   - Análise estática de código
   - Detecção de vulnerabilidades

4. **TruffleHog**
   - Detecção de secrets no código
   - Scan de histórico Git

### Configurações de Segurança

- Usuário não-root nos containers
- Secrets gerenciados via GitHub Secrets
- Certificados SSL para HTTPS (produção)
- Rate limiting no Nginx

## 📊 Monitoramento

### Health Checks

**Aplicação:**
```bash
curl http://localhost:8080/health
```

**Banco de dados:**
```bash
docker exec fintech-postgres pg_isready
```

**Redis:**
```bash
docker exec fintech-redis redis-cli ping
```

### Logs

**Estrutura:**
```
logs/
├── development/
├── staging/
└── production/
```

**Configuração:**
- Logs estruturados por ambiente
- Rotação automática
- Níveis configuráveis

## 🚀 Deploy

### Deploy Automático

**Staging:**
- Trigger: Push para `develop`
- Ambiente: `staging`
- Aprovação: Automática

**Produção:**
- Trigger: Push para `main`
- Ambiente: `production`
- Aprovação: Manual (configurável)

### Deploy Manual

```bash
# Staging
docker-compose -f docker-compose.yml -f docker-compose.staging.yml up -d

# Produção
docker-compose -f docker-compose.yml -f docker-compose.production.yml up -d
```

### Rollback

```bash
# Via GitHub Actions
gh workflow run rollback.yml

# Manual
docker-compose down
docker-compose -f docker-compose.yml -f docker-compose.production.yml up -d
```

## 🔧 Configuração

### Variáveis de Ambiente

**Development** (`env.development`):
```env
APP_ENV=development
APP_PORT=8080
JAVA_OPTS=-Xmx256m -Xms128m
POSTGRES_DB=fintech_dev
LOG_LEVEL=DEBUG
```

**Staging** (`env.staging`):
```env
APP_ENV=staging
APP_PORT=8080
JAVA_OPTS=-Xmx512m -Xms256m
POSTGRES_DB=fintech_staging
LOG_LEVEL=INFO
```

**Production** (`env.production`):
```env
APP_ENV=production
APP_PORT=8080
JAVA_OPTS=-Xmx1024m -Xms512m
POSTGRES_DB=fintech_prod
LOG_LEVEL=WARN
```

### GitHub Secrets

Configure os seguintes secrets no GitHub:

```
GITHUB_TOKEN          # Token automático
DOCKER_USERNAME       # Usuário Docker Hub (se usar)
DOCKER_PASSWORD       # Senha Docker Hub (se usar)
STAGING_HOST          # Host do ambiente staging
PRODUCTION_HOST       # Host do ambiente produção
SLACK_WEBHOOK         # Webhook Slack para notificações
```

## 📈 Métricas e Alertas

### Métricas Coletadas

- Cobertura de testes
- Vulnerabilidades de segurança
- Performance da aplicação
- Uso de recursos
- Disponibilidade dos serviços

### Alertas

- Falhas no pipeline
- Vulnerabilidades críticas
- Falhas de deploy
- Problemas de saúde dos serviços

## 🛠️ Comandos Úteis

### Desenvolvimento

```bash
# Iniciar ambiente completo
docker-compose up -d

# Ver logs
docker-compose logs -f fintech-app

# Executar testes
mvn test

# Build da aplicação
mvn clean package

# Build da imagem Docker
docker build -t fintech-app:dev .
```

### Produção

```bash
# Deploy
docker-compose -f docker-compose.yml -f docker-compose.production.yml up -d

# Verificar saúde
curl http://localhost/health

# Backup do banco
docker exec fintech-postgres pg_dump -U fintech_user fintech_prod > backup.sql

# Restart de serviço
docker-compose restart fintech-app
```

## 🔍 Troubleshooting

### Problemas Comuns

1. **Falha no build Docker**
   ```bash
   # Verificar logs
   docker build --no-cache -t fintech-app .
   ```

2. **Problemas de conectividade**
   ```bash
   # Verificar rede
   docker network ls
   docker network inspect fintech_fintech-network
   ```

3. **Falhas de teste**
   ```bash
   # Executar testes com debug
   mvn test -X
   ```

4. **Problemas de memória**
   ```bash
   # Ajustar JAVA_OPTS
   export JAVA_OPTS="-Xmx512m -Xms256m"
   ```

## 📚 Recursos Adicionais

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [OWASP Dependency Check](https://owasp.org/www-project-dependency-check/)
- [Trivy Security Scanner](https://trivy.dev/)

## 🤝 Contribuição

Para contribuir com melhorias no pipeline:

1. Crie uma branch feature
2. Implemente as mudanças
3. Execute os testes localmente
4. Abra um Pull Request
5. Aguarde a aprovação e merge

---

**Última atualização**: $(date)
**Versão do pipeline**: 1.0.0
