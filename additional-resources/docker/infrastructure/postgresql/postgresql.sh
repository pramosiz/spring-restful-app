#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for PostgreSQL
docker run -d --name postgres \
  --network restful-app \
  -p 5432:5432 \
  -e POSTGRES_DB=test \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -v /Users/pabloramosizquierdo/Desktop/Pablo/Tecnologias/Dockers/PostgreSQL/data:/var/lib/postgresql/data \
  postgres:14.13
  