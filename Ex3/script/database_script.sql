create database mydb;
use mydb;
Create table book (
                      id int auto_increment primary key,
                      name varchar(255),
                      author varchar(255),
                      status TINYINT(1) not null default 0,
                      quantity int
);
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       role_name VARCHAR(50) UNIQUE NOT NULL
);
CREATE TABLE user_roles (
                            user_id INT,
                            role_id INT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
);
insert into roles (id,role_name) values (1,'Admin'),(2,'Manager'),(3,'Student');
drop table book;
Select * from book;
SELECT * FROM book WHERE name = 'Sachin';