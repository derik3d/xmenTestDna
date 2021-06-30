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
