CREATE SCHEMA IF NOT EXISTS "user-app";

CREATE TABLE IF NOT EXISTS "user-app".users
(
    id SERIAL,
    name varchar(100) NOT NULL COLLATE pg_catalog."default",
    email varchar(100) COLLATE pg_catalog."default",
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "user-app".cars
(
    id SERIAL,
    brand varchar(100) NOT NULL COLLATE pg_catalog."default",
    model varchar(100) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT cars_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "user-app".bikes
(
    id SERIAL,
    brand varchar(100) NOT NULL COLLATE pg_catalog."default",
    model varchar(100) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT bikes_pk PRIMARY KEY (id)
);
