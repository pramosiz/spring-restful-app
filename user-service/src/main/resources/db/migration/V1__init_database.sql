CREATE SCHEMA IF NOT EXISTS user_service;

-- Lo ideal es que cada microservicio tenga su propio esquema
CREATE TABLE IF NOT EXISTS user_service.users
(
    id SERIAL,
    name varchar(100) NOT NULL COLLATE pg_catalog."default",
    email varchar(100) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_service.cars
(
    id SERIAL,
    brand varchar(100) NOT NULL COLLATE pg_catalog."default",
    model varchar(100) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT cars_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_service.bikes
(
    id SERIAL,
    brand varchar(100) NOT NULL COLLATE pg_catalog."default",
    model varchar(100) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT bikes_pkey PRIMARY KEY (id)
);


