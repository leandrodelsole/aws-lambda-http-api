#!/usr/bin/env bash

cd "${0%/*}"

printf "\n\napagando dados na AWS através do Terraform\n\n"
cd ../terraform
terraform destroy -auto-approve



