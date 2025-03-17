#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for car-service
docker run -d \
  --network restful-app \
  -p 8761:8761 \
  -e CONFIG_SERVER_URI=config-service \
  -e CONFIG_SERVER_PORT=8888 \
  -e EUREKA_SERVER_URI=localhost \
  --name eureka-service eureka-service:1.0.0
