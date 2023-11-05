CREATE TABLE IF NOT EXISTS user_service.users
(
    id SERIAL,
    name varchar(100) NOT NULL COLLATE pg_catalog."default",
    email varchar(100) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
