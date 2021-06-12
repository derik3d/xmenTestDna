#!/bin/bash
rm -f create.sql
cp ./../../target/sql/create.sql ./create.sql
docker build .