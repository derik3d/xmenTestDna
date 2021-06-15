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
