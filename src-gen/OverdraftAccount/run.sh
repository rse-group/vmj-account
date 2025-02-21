#!/bin/bash
source ~/.zshrc  

cleanup() {
    pkill -P $$
    rm java.log
    exit 1
}

trap cleanup SIGINT

java -cp bankaccount.product.overdraftaccount --module-path bankaccount.product.overdraftaccount -m bankaccount.product.overdraftaccount 2>&1 | tee java.log &
JAVA_PID=$!
TEE_PID=$(pgrep -n tee)
tail -f java.log --pid=$TEE_PID | while read -r LINE; do
    if [[ "$LINE" == *"== CREATING OBJECTS AND BINDING ENDPOINTS =="* ]]; then
        break
    fi
done

echo "SELECT 'CREATE DATABASE bankaccount_product_overdraftaccount' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bankaccount_product_overdraftaccount') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/bankaccount_product_overdraftaccount"
done

wait