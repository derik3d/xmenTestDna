#!/bin/bash

docker network create -d bridge magneto-network
docker run --network=magneto-network -p 3306:3306 -d --name magnetodb magnetodb
docker run --network=magneto-network -p 8080:8080 -d --name magnetoservice magnetoservice