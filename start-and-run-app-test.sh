#!/bin/bash

ENV_FILE=".env.test"
COMPOSE_FILE="docker-compose-test.yml"

if [ ! -f $ENV_FILE ]; then
    echo "Error: $ENV_FILE file not found!"
    exit 1
fi

echo "Starting the test environment..."
docker compose -f $COMPOSE_FILE --env-file $ENV_FILE up --build