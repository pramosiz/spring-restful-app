#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for car-service
docker run -d \
  --network restful-app \
  -p 8002:8002 \
  -e PG_DB_URL=postgres \
  -e PG_DB_PORT=5432 \
  -e PG_DB_NAME=test \
  -e PG_DB_USER=postgres \
  -e PG_DB_PWD=postgres \
  -e PG_DB_SCHEMA=user-app \
  -e RABBITMQ_HOST=rabbitmq_user \
  -e RABBITMQ_PORT=5672 \
  -e RABBITMQ_USER=test \
  -e RABBITMQ_PWD=test \
  -e RABBITMQ_VHOST=test \
  --name car-service car-service:1.0.0
