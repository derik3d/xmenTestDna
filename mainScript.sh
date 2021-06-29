#!/bin/bash

#create sql dbscript
mvn clean verify -Dspring.profiles.active=dev,db


#make jar image
cd ./scripts/db
./dbBuilding.sh


#go back
cd ./../../

#generate documentation
mvn verify jacoco:report -DskipTests

#get jar
mvn clean package -Dspring.profiles.active=prod -Dmaven.test.skip=true


#make jar image
cd ./scripts/app
./appBuilding.sh


#go back
cd ./../../
