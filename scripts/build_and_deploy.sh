#!/usr/bin/env bash

cd "${0%/*}"
cd ../


printf '\n\nClean Verify do Projeto\n\n'
mvn clean verify
if [ $? -ne 0 ]; then
  printf '\n\nClean Verify falhou, deploy abortado\n'
  exit -1
fi

rm -rf target/server
rm -rf target/package

mkdir -p target/server

printf '\n\nbuild da GraalVM\n\n'
#docker run --rm --name graal -v $(pwd):/working oracle/graalvm-ce:1.0.0-rc16 \
#    /bin/bash -c "native-image --enable-url-protocols=http --enable-url-protocols=https \
#                    -Djava.net.preferIPv4Stack=true \
#                    -H:ReflectionConfigurationFiles=/working/runtime/reflect.json \
#                    --delay-class-initialization-to-runtime=javax.net.ssl.HttpsURLConnection \
#                    --no-fallback \
#                    --no-server -jar /working/target/aws-lambda-http-api-1.0-SNAPSHOT.jar \
#                    --enable-all-security-services \
#                    ; \
#                    cp aws-lambda-http-api-1.0-SNAPSHOT /working/target/server"
#
#                    --initialize-at-run-time=javax.net.ssl.HttpsURLConnection \
docker run --rm --name graalvm-aws-lambda-http-api -v $(pwd):/working oracle/graalvm-ce:latest \
    /bin/bash -c "gu install native-image ; \
					native-image --enable-url-protocols=http --enable-url-protocols=https \
                    -Djava.net.preferIPv4Stack=true \
                    -H:ReflectionConfigurationFiles=/working/runtime/reflect.json \
                    --initialize-at-build-time=software.amazon.awssdk.protocols.core.StringToValueConverter\$SimpleStringToValue \
                    --no-fallback --allow-incomplete-classpath \
                    --no-server -jar /working/target/aws-lambda-http-api-1.0-SNAPSHOT.jar \
                    --enable-all-security-services \
                    ; \
                    cp aws-lambda-http-api-1.0-SNAPSHOT /working/target/server"

if [ $? -ne 0 ]; then
  printf '\n\nbuild da GraalVM falhou, deploy abortado\n'
  exit -1
fi


cp -R target/server target/package

cp runtime/bootstrap target/package
#peguei da OpenJDK11 para Linux
cp runtime/libsunec.so target/package

chmod +x target/package/*

zip -j target/package/aws-lambda-http-api-runtime-and-native-image.zip target/package/*


printf '\n\nLambda: Iniciando Terraform !\n\n'
cd terraform

terraform init

printf '\n\n\n\n'
terraform apply -auto-approve