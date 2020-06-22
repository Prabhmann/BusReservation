#!/bin/bash
sudo docker run --rm --name pg_bus_db -d -p 5432:5432 -e POSTGRES_USER=bus -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=bus_db postgres:9.6
./gradlew bootRun
