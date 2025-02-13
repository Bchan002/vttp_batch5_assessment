# Task 1.1 
create database movies;

use movies;

create table imdb(

    imbd_id varchar(16) not null,
    vote_average float default 0, 
    vote_count int default 0, 
    release_date date,
    revenue decimal(15,2) default 10000000,
    budget decimal(15,2) default 10000000,
    runtime int default 90,
 
    constraint pk_imbd_id primary key (imbd_id)

);

