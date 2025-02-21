#!/bin/bash

cleanup() {
    echo "Exiting script..."
    pkill -P $$
    exit 1
}

trap cleanup SIGINT

read -p "Enter the path to the frontend directory: " frontend_dir

echo "SELECT 'CREATE DATABASE bankaccount_product_overdraftaccount' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bankaccount_product_overdraftaccount') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/bankaccount_product_overdraftaccount"
done

java -cp bankaccount.product.overdraftaccount --module-path bankaccount.product.overdraftaccount -m bankaccount.product.overdraftaccount &

cd $frontend_dir && {
    npm install && {
        npm run json:server &
        npm run start &
    }
}

wait