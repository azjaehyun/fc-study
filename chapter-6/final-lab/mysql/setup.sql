-- create the databases
CREATE DATABASE IF NOT EXISTS myapp;

-- create the users for each database
CREATE USER 'myapp'@'%' IDENTIFIED BY 'myapp';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `myapp`.* TO 'myapp'@'%';

FLUSH PRIVILEGES;
