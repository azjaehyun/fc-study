#!/bin/bash

if [ -z $MYSQL_ROOT_PASSWORD ]; then
  exit 1
fi

mysql_install_db --user mysql > /dev/null

#cat > /tmp/sql <<EOF
#USE mysql;
#FLUSH PRIVILEGES;
#GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
#UPDATE user SET password=PASSWORD("$MYSQL_ROOT_PASSWORD") WHERE user='root';
#create database projectdb
#show grants for root@'%';
#grant select, insert, update on projectdb.* to 'root'@'%';
#EOF

cat > /tmp/sql <<EOF
USE mysql;
FLUSH PRIVILEGES;
create database projectdb
show grants for developer@'%';
grant select, insert, update on projectdb.* to 'developer'@'%';
EOF

mysqld --bootstrap --verbose=0 < /tmp/sql
rm -rf /tmp/sql

mysqld