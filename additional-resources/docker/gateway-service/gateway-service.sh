#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Run the Docker container for car-service
docker run -d \
  --network restful-app \
  -p 8080:8080 \
  -e CONFIG_SERVER_URI=config-service \
  -e CONFIG_SERVER_PORT=8888 \
  -e EUREKA_SERVER_URI=eureka-service \
  -e EUREKA_SERVER_PORT=8761 \
  --name gateway-service gateway-service:1.0.0
