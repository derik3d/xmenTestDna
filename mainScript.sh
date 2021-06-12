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

docker network create -d bridge magneto-network
docker run --network=magneto-network -p 3306:3306 -d --name magnetodb magnetodb
docker run --network=magneto-network -p 8080:8080 -d --name magnetoservice magnetoservice


