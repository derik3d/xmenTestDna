# Magneto's mutant repository
## Are you a mutant? or not!.

### Requirements
- bash (could be git for windows bash)
- JDK 11+
- maven
- docker
- terraform cli

## Features
### Solution features
- Classify human or mutant by dna sequence
- Obtain statistics of the data analized

### Project features
- Profiles for building SQL schema, tests run and production JAR
- Automated building and publishing of docker images of services (buisness and db images)
- Automated cloud infrasture with terraform (needed from aws: IAM user, ssh key pair for ec2 and config to save tf state over S3)

##Documentation

- Coverage report 	: ./target/site/jacoco/index.html
- Api docs			: ./API/index.html