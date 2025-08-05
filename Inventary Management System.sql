-- creating new database named "Inventary".
create database Inventary;

-- selecting database to create table. 
use Inventary;

-- Creating table "product_stock" 
create table product_stock(id int auto_increment primary key,name varchar(255),quantity int , price double );
-- here id column is the primary column and auto increment 
