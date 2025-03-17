#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for bike-service
docker run -d \
  --network restful-app \
  -p 8888:8888 \
  -e REPOSITORY_BRANCH=develop-servlet \
  -e REPOSITORY_URL=https://github.com/pramosiz/spring-restful-app \
  -e CONFIG_FILE_ROUTE=additional-resources/config-data \
  --name config-service config-service:1.0.0
