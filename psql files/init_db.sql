--User creation:
CREATE USER product_manager_user WITH password '123456';
--Database creation:
CREATE DATABASE product_management_db OWNER product_manager_user;
--User privileges:
GRANT ALL PRIVILEGES ON DATABASE product_management_db TO product_manager_user;

--Connect to database as product_manager_user:
-- => psql -U product_manager_user -d product_management_db