# AWS Lambda HTTP API

Este projeto tem a intenção de demonstrar uma implementação bem simplista de um CRUD em uma API HTTP com AWS Lambda, utilizando API Gateway e DynamoDB.

A organização das classes foi feita para lembrar um projeto Spring Boot. Bastaria mudar as classes do pacote controller, adicionar algumas anotações ao projeto, então ele poderia ser convertido para utilizar o SpringBoot.

Para criação e deploy da infraestrutura foi usado o Terraform.


## Pré-requisitos
* [JDK 1.8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/download.cgi) 3.5.4 ou superior
* [Terraform](https://www.terraform.io/downloads.html) v0.11.12 ou superior
* [Conta AWS](https://portal.aws.amazon.com/billing/signup)

## Setup na AWS

**Pode gerar custo e o autor deste repositório não deve ser responsabilizado por isso.** 
No momento em que foi publicada este código, quem está no período de um ano de experimentação da AWS não é cobrado pela criação e execução desta infraestrutura em caráter de testes.

Consulte o arquivo `variables.tf` para saber as informações necessárias para o processo. Então, crie um arquivo `variables.dev.auto.tfvars` [com o formato adequado](https://learn.hashicorp.com/terraform/getting-started/variables.html) para fornecer os valores para as variáveis. Exemplo:
```
account_id = "123456789012"
access_key = "AWS123AWS123AWS123AW"
secret_key = "naovoucontar12345naovoucontar12345naovou"
```


Entre na pasta scripts e execute o script `build_and_deploy.sh`. 

**Não esqueça**, para evitar custos, ao final dos testes execute os passos da seção _não esqueça_.

 
## Desenvolvimento

Caso faça alterações no API Gateway, lembre que é preciso recriá-lo para que tenha efeito (o Terraform não consegue fazer um diff, [conforme o tutorial deles](https://learn.hashicorp.com/terraform/aws/lambda-api-gateway#making-changes-to-the-api-gateway-configuration)).

Para alterar o código do lambda, basta reexecutar o script `build_and_deploy.sh` novamente, como feito no Setup.

## Execução

Ao final do Setup, são exibidos comandos curl para os endpoints existentes. Basta executá-los, ou traduzi-los para outra ferramenta, como o Postman por exemplo.

## Não esqueça

Execute o `destroy.sh` antes de ir embora :)

Ele irá apagar todos os recursos gerenciados pelo Terraform, criados por este projeto.
Para limpar completamente sua conta AWS, é preciso apagar manualmente os logs criados pelos Lambdas, presentes no CloudWatch.