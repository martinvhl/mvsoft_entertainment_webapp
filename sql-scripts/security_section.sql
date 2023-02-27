/*DDL Script to create an securtity part of database for app, adjusted to MariaDB v.10*/
CREATE DATABASE security_section;
USE security_section;

create table user(
    user_id int(10) not null primary key AUTO_INCREMENT,
    username varchar(50) not null,
    email varchar(60) not null,
    password varchar(60) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null
) engine InnoDB AUTO_INCREMENT=1;

create table role(
    role_id int(10) not null primary key AUTO_INCREMENT,
    name varchar(20) not null
)engine InnoDB AUTO_INCREMENT=1;

create table users_roles(
    role_id int(10) not null,
    user_id int(10) not null,
    primary key (role_id,user_id),
    foreign key (role_id) REFERENCES roles(role_id),
    foreign key (user_id) REFERENCES users(user_id)
)engine InnoDB;