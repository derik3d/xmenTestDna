# Magneto's mutant repository
## Are you a mutant? or not!.

### Requirements
- Bash (could be git for windows bash)
- JDK 11+
- Maven
- Docker
- Aws cli
- Terraform cli

## Features
### Solution features
- Classify human or mutant by dna sequence
- Obtain statistics of the data analized

### Project features
- Profiles for building SQL schema, tests execution and production JAR
- Automated building and publishing of docker images of services (buisness and db images)
- Automated cloud infrasture with terraform (needed from aws: IAM user, ssh key pair for ec2 and configuration to save tf state over S3)

## Documentation

- Coverage report 	: ./target/site/jacoco/index.html (96%)
- Api docs			: ./API/index.html

## How to run?

### Packing (step 1)
Compile and generate: db schema, executable and coverage and then pack into docker images, the service and the db apart.

Run the main script on bash

```sh
./mainScript.sh
```
done!

Well, lets review mainScript.sh:

```sh
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
```

This script call two more scripts: appBuilding.sh and dbBuilding.sh
Those two, execute each one, a docker file, to build each image.

Lets review them!:

 
*appBuilding.sh*

```sh
#!/bin/bash
rm -f xmenTestDna-0.0.1-SNAPSHOT.jar
cp ./../../target/xmenTestDna-0.0.1-SNAPSHOT.jar ./xmenTestDna-0.0.1-SNAPSHOT.jar
docker build -t magnetoservice:latest .
rm -f xmenTestDna-0.0.1-SNAPSHOT.jar
```

*DOCKERFILE*

```sh
FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY xmenTestDna-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

 
*appBuilding.sh*

```sh
#!/bin/bash
rm -f create.sql
cp ./../../target/sql/create.sql ./create.sql
docker build -t magnetodb:latest .
rm -f create.sql
```

*DOCKERFILE*

```sh
FROM mysql:5.7.15

MAINTAINER me

ENV MYSQL_DATABASE=magneto \
    MYSQL_ROOT_PASSWORD=root

ADD create.sql /docker-entrypoint-initdb.d

EXPOSE 3306
```




### How to run locally, if needed (optional)

- Follow the steps till here
- run the script localRunScript.sh on bash

```sh
./localRunScript.sh
```

Lets see the script!

```sh

#!/bin/bash

docker network create -d bridge magneto-network
docker run --network=magneto-network -p 3306:3306 -d --name magnetodb magnetodb
docker run --network=magneto-network -p 8080:8080 -d --name magnetoservice magnetoservice

```

that script creates a docker network where the images could work together.

### Push images to docker (step 2)

Requirements
- authenticate to docker desktop or provide credentials to docker cli

Then, to push local images just generated to docker, only execute the following script:

```sh
./pushToDocker.sh
```

Thats all, done!

Lets review the script:

```sh
#!/bin/bash

docker tag magnetoservice derik3d/magnetoservice:latest
docker tag magnetodb derik3d/magnetodb:latest

docker push derik3d/magnetoservice:latest
docker push derik3d/magnetodb:latest
```

Remember replace *derik3d*, the docker repository name actually used, for the one you will use, here and in the terraform ec2 instance building script.

### Build aws cloud and mount images (step 3)

Requirements (manually)

Create aws IAM super user to get credentials and supply them to terraform

- Create an IAM user, give it full admin rights
- Then download the credentials file when creating the access key as .csv, it will give you the option on the creation pop up
- There is a file on templates called envVarsToBeLoadedBefore.txt, it has the format on which you should feed the access keys to Bash replacing the credentials for the '*' and executing those commands, ensuring with that, access to aws when executing the terraform commands
- REMEMBER, every time you need to execute terraform commands you need to do the last step to load the credentials to that bash session

Configure aws to save terraform state over the cloud

- Create a DynamoDB table named terraform-lock with a primary partition key named LockID of type string.

Add key pair on aws and download the .pem to communicate with the ec2 instance if gets necessary

- On the aws console, on the section: EC2/Network&Security/KayPAirs, choose create pair with the name mutant-key and download the .pem file


Next, the cloud infrastructure creation!!

*Having the credentials loaded into bash*

execute cloudBuild.sh, when this command is done, there will appear the public ip of the site as a console output as server_public_ip, write it down!

*Its better the first time to use the terraform init as an alone command given that it will ask your approval with the external use of the terraform state file*

```sh
#!/bin/bash

#go to tf templates folder
cd ./templates

#initialize terraform
terraform init

#plan and review the infra
terraform plan

#execute the creation
terraform apply --auto-approve


#if you need to destroy all infra created here
#terraform destroy
```

The application should be ready for action!

Those commands are run on the folder templates, there is one command you should use when you don't need the site, to eliminate all, the terraform destroy command.

If you need to use the destroy command alone or any other

- Go to the templates folder
- Load the credentials into bash
- Execute the command

Ok! All manual work is finished!


#What have we done!!!???? (and how)

- A business service that examines the dna sequense given in the correct format and verifies if it corresponds to the mutant condition and then saves the results on a db
- A db service that saves the results of the dna analisys, this data is later used for statistics
- Why the db and business logic are separated images? later the image of the service, if needed could be replicated with multiple instances and a load balancer to handle more traffic
- Automate the compile app, dockerize, upload images and create cloud infra tasks with very useful sh scripts on many cases.

- Why bash? simplify your life configuring scripts that do the repetitive work


- Why docker? configure once, run a mirror copy everywhere


- Why terraform? configure once, don't worry about launching your cloud architecture to any other aws account


- Why I'm not a mutant? wait, I haven't sent my dna sequence yet... (runs fast to buy a genetic sequencer, then realizes those are too expensive)


#Where to find the challenge related logic code

- MutantService.java : summary, solves the matrix problem
- MainService.java : Manages mutant service, implements the little stats logic and communicates to db

