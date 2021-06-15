#!/bin/bash

docker tag magnetoservice derik3d/magnetoservice:latest
docker tag magnetodb derik3d/magnetodb:latest

docker push derik3d/magnetoservice:latest
docker push derik3d/magnetodb:latest

