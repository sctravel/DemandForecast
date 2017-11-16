mysqldump -h  demandforecast.cbcwdscsgece.us-west-2.rds.amazonaws.com -u admin -p Inventory > dump.sql
create database Inventory

mysql -h localhost -u root --password=test Inventory < dump.sql
mysql -u root -p

sudo apt-get update
sudo apt-get install mysql-server
sudo mysql_secure_installation
sudo mysql_install_db

https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-14-04
