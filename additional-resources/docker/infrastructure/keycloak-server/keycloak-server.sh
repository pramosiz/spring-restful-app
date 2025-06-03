#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

docker run -d \
  --network restful-app \
  -e KEYCLOAK_HTTP_PORT=8091 \
  -e KEYCLOAK_DATABASE_HOST=postgres \
  -e KEYCLOAK_DATABASE_PORT=5432 \
  -e KEYCLOAK_DATABASE_USER=postgres \
  -e KEYCLOAK_DATABASE_PASSWORD=postgres \
  -e KEYCLOAK_DATABASE_SCHEMA=keycloak \
  -e KEYCLOAK_DATABASE_NAME=test \
  -p 8091:8091 \
  --name keycloak-server bitnami/keycloak:26.1.4