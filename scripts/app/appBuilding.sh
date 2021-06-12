#!/bin/bash
rm -f xmenTestDna-0.0.1-SNAPSHOT.jar
cp ./../../target/xmenTestDna-0.0.1-SNAPSHOT.jar ./xmenTestDna-0.0.1-SNAPSHOT.jar
docker build -t magnetoservice:latest .
rm -f xmenTestDna-0.0.1-SNAPSHOT.jar