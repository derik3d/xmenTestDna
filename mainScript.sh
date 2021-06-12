#!/bin/bash

#create sql dbscript
mvn clean verify -Dspring.profiles.active=dev,db


#make jar image
cd ./scripts/db
./dbBuilding.sh


#go back
cd ./../../


#get jar
mvn clean package -Dspring.profiles.active=prod -Dmaven.test.skip=true


#make jar image
cd ./scripts/app
./appBuilding.sh


#go back
cd ./../../


docker run -p 3306:3306 -d magnetodb
docker run -p 8080:8080 -d magnetoservice


