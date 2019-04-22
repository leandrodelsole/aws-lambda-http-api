#!/usr/bin/env bash

cd ../
printf '\n\nClean Verify do Projeto\n\n'
mvn clean verify
if [ $? -ne 0 ]; then
  printf '\n\nClean Verify falhou, deploy abortado\n'
  exit -1
fi

printf '\n\nLambda: Iniciando Terraform !\n\n'
cd terraform

terraform init

printf '\n\n\n\n'
terraform apply -auto-approve