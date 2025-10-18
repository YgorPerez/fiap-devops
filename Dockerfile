# Multi-stage build para otimizar o tamanho da imagem
FROM openjdk:17-jdk-slim AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar código fonte Java
COPY src/ ./src/

# Compilar a aplicação Java
RUN javac -d ./target/classes ./src/classes/*.java ./src/Main.java

# Stage de produção
FROM openjdk:17-jre-slim

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Criar usuário não-root para segurança
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Definir diretório de trabalho
WORKDIR /app

# Copiar classes compiladas do stage de build
COPY --from=build /app/target/classes ./classes

# Copiar scripts de inicialização
COPY docker-entrypoint.sh ./
RUN chmod +x docker-entrypoint.sh

# Alterar propriedade dos arquivos para o usuário appuser
RUN chown -R appuser:appuser /app

# Mudar para usuário não-root
USER appuser

# Expor porta da aplicação
EXPOSE 8080

# Definir variáveis de ambiente
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV APP_ENV="production"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/health || exit 1

# Comando de inicialização
ENTRYPOINT ["./docker-entrypoint.sh"]
