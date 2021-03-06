#!/usr/bin/env bash

if [ $(uname -m) == "armv7l" ];
then
    java -d32 -server -XX:+UseG1GC -Xms768m -Xmx768m -jar target/microservice3-1.0-SNAPSHOT.jar
else
    java -d64 -server -XX:+UseG1GC -Xms768m -Xmx768m -jar target/microservice3-1.0-SNAPSHOT.jar
fi
