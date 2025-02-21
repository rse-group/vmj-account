echo SELECT 'CREATE DATABASE bankaccount_product_overdraftaccount' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bankaccount_product_overdraftaccount') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/bankaccount_product_overdraftaccount"

java -cp bankaccount.product.overdraftaccount --module-path bankaccount.product.overdraftaccount -m bankaccount.product.overdraftaccount