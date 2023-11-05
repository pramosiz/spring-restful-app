CREATE TABLE IF NOT EXISTS user_service.cars
(
    id SERIAL,
    brand varchar(100) NOT NULL COLLATE pg_catalog."default",
    model varchar(100) COLLATE pg_catalog."default",
    user_id bigint,
    CONSTRAINT cars_pkey PRIMARY KEY (id)
)
