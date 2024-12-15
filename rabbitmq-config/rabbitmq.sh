#!/bin/bash

# Crear una red Docker si no existe
docker network create restful-app || true

# Ejecutar el contenedor de RabbitMQ
docker run -d --name rabbitmq_user \
  --network restful-app \
  -p 15672:15672 \
  -p 5672:5672 \
  rabbitmq_user:v1
