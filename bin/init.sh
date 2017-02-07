#!/bin/bash

echo "starting mysql"
mysql.server start

echo "add JDBC"
sudo cp mysql-connector-java-5.1.40-bin.jar /Library/Java/Extensions

echo "mysql init db with jdbc user"
mysql -u root -e "CREATE DATABASE CS157B DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci; CREATE USER 'java'@'localhost' IDENTIFIED BY '1234'; GRANT ALL ON CS157B.* TO 'java'@'localhost' IDENTIFIED BY '1234';"

echo "jdbc URL: jdbc:mysql://localhost:3306/CS157B"
echo "jdbc user:pw is java:1234"