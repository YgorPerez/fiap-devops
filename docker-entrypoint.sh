#!/bin/bash

# Script de inicialização para a aplicação Java
set -e

echo "Iniciando aplicação Fintech..."

# Verificar se as classes foram compiladas
if [ ! -d "./classes" ]; then
    echo "Erro: Classes não encontradas. Execute a compilação primeiro."
    exit 1
fi

# Definir classpath
export CLASSPATH="./classes"

# Executar a aplicação principal
echo "Executando aplicação Java..."
java $JAVA_OPTS -cp $CLASSPATH TesteFintech

echo "Aplicação finalizada."
