#!/bin/bash
set -e

if [ -f ./gradlew ]; then
	./gradlew build
else
	./mvnw package
fi

# TODO
#docker run --rm --name appsvr -p 80:8080 -v $(pwd)/target/carz.war:/opt/jboss/wildfly/standalone/deployments/carz.war jboss/wildfly:10.1.0.Final /opt/jboss/wildfly/bin/standalone.sh --server-config=standalone-full.xml -b "0.0.0.0"
