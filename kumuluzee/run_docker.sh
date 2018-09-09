#!/bin/bash
set -e

if [ -f ./gradlew ]; then
	./gradlew build
else
	./mvnw package
fi

docker run --rm --name appsvr -p 80:8080 -v "$(pwd)/target/carz.jar:/opt/carz.jar" openjdk:8 java -jar /opt/carz.jar
