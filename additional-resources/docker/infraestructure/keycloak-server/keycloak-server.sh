#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

docker run -d \
  --network restful-app \
  -p 8091:8080 \
  -e KEYCLOAK_DATABASE_HOST=postgres \
  -e KEYCLOAK_DATABASE_PORT=5432 \
  -e KEYCLOAK_DATABASE_USER=postgres \
  -e KEYCLOAK_DATABASE_PASSWORD=postgres \
  -e KEYCLOAK_DATABASE_SCHEMA=keycloak \
  -e KEYCLOAK_DATABASE_NAME=test \
  --name keycloak-server bitnami/keycloak:26.1.4