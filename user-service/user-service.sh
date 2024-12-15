#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for user-service
docker run -d --name user_service \
  --network restful-app \
  -p 8001:8001 \
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
  --name user-service-container user-service:1.0.0
