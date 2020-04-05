To create database in mysql follow the below steps:
1. Sign in MYSQL using `root` user :`mysql -u root -p`
2. Create user in mysql for your db: `GRANT ALL PRIVILEGES ON *.* TO 'udacity'@'localhost' IDENTIFIED BY 'udacity';`
3. Create database with : `CREATE DATABASE super_duper_drive`
4. Create tables: `mysql -u "udacity" --database="super_duper_drive" --password="udacity" < starter/cloudstorage/src/main/resources/schema.sql`