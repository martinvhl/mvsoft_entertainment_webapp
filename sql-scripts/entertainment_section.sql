/*DDL Script to create an entertainment database of app, adjusted to MariaDB v.10*/
drop database entertainment_section;
CREATE DATABASE entertainment_section;
USE entertainment_section;

CREATE TABLE films (
    film_id int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(100) NOT NULL,
    subtitle varchar(100),
    director varchar(100) NOT NULL,
    length int(3),
    year int(4) not null,
    cover mediumblob,
    description varchar(1600),
    created_date Timestamp not null DEFAULT CURRENT_TIMESTAMP,
    last_changed_date Timestamp not null DEFAULT CURRENT_TIMESTAMP
) ENGINE InnoDB AUTO_INCREMENT=1;

CREATE TABLE actors (
    actor_id int(10) not null AUTO_INCREMENT PRIMARY Key,
    name VARCHAR(100) not null
) ENGINE InnoDB AUTO_INCREMENT=1;

create table actors_in_films (
    film_id int(10) NOT null,
    actor_id int(10) NOT NULL,
    PRIMARY KEY (film_id,actor_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (actor_id) REFERENCES actors(actor_id)
) ENGINE InnoDB;

create table users(
    user_id int(10) not null primary key,
    user_name varchar(50) not null,
    login varchar(30) not null
) ENGINE InnoDB AUTO_INCREMENT=1;

create table users_films(
    user_id int(10) not null,
    film_id int(10) not null,
    Primary key (user_id,film_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    Foreign key (user_id) REFERENCES users(user_id)
) ENGINE InnoDB;

create table studios(
    studio_id int(10) primary key AUTO_INCREMENT,
    name varchar(50) not null,
    country varchar(20)
) engine InnoDB AUTO_INCREMENT=1;

create table games(
    game_id int(10) not null AUTO_INCREMENT primary key,
    title varchar(100) not null,
    developer int(10) not null,
    year int(4) not null,
    description varchar(1600),
    cover mediumblob, 
    created_date Timestamp not null DEFAULT CURRENT_TIMESTAMP,
    last_changed_date Timestamp not null DEFAULT CURRENT_TIMESTAMP,
    FOREIGN key (developer) REFERENCES studios(studio_id)
) ENGINE InnoDB AUTO_INCREMENT=1;

create table users_games(
    user_id int(10) not null,
    game_id int(10) not null,
    Primary key (user_id,game_id),
    FOREIGN key (user_id) REFERENCES users(user_id),
    foreign key (game_id) REFERENCES games(game_id)
) engine InnoDB;

