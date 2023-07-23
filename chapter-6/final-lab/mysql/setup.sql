-- create the databases
CREATE DATABASE IF NOT EXISTS application;

-- create the users for each database
CREATE USER 'application'@'%' IDENTIFIED BY 'application';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `application`.* TO 'application'@'%';

-- create the databases
CREATE DATABASE IF NOT EXISTS jobposting;

-- create the users for each database
CREATE USER 'jobposting'@'%' IDENTIFIED BY 'jobposting';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `jobposting`.* TO 'jobposting'@'%';

FLUSH PRIVILEGES;
