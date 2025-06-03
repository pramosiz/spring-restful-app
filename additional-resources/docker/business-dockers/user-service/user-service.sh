#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for user-service
# -p 8001:8001 \ # Uncomment for Swagger UI
docker run -d \
  --network restful-app \
  -e CONFIG_SERVER_URI=config-service \
  -e CONFIG_SERVER_PORT=8888 \
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
  -e EUREKA_SERVER_URI=eureka-service \
  -e EUREKA_SERVER_PORT=8761 \
  -e KEYCLOAK_SERVER_URI=keycloak-server \
  -e KEYCLOAK_SERVER_PORT=8091 \
  --name user-service user-service:1.0.0
