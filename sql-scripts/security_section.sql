/*DDL Script to create an securtity part of database for app, adjusted to MariaDB v.10*/
CREATE DATABASE security_section;
USE security_section;

create table users(
    user_id int(10) not null primary key AUTO_INCREMENT,
    user_name varchar(50) not null,
    email varchar(60) not null,
    login varchar(30) not null,
    password varchar(60) not null
) engine InnoDB AUTO_INCREMENT=1;

create table roles(
    role_id int(10) not null primary key AUTO_INCREMENT,
    role_name varchar(20) not null
)engine InnoDB AUTO_INCREMENT=1;

create table users_roles(
    role_id int(10) not null,
    user_id int(10) not null,
    primary key (role_id,user_id),
    foreign key (role_id) REFERENCES roles(role_id),
    foreign key (user_id) REFERENCES users(user_id)
)engine InnoDB;