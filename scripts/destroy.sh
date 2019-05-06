#!/usr/bin/env bash

cd "${0%/*}"
cd ../terraform
terraform destroy -auto-approve