#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Descargar y ejecutar Zipkin
docker run -d \
    --network restful-app \
    -p 9411:9411 \
    --name zipkin \
    bitnami/zipkin:3.4.4