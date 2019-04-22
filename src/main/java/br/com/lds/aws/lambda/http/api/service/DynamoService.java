package br.com.lds.aws.lambda.http.api.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public final class DynamoService {

	//se fosse spring, transformaríamos em um bean, por exemplo
	private static AmazonDynamoDB client;

	public DynamoService() {}

	public AmazonDynamoDB getClient() {
		if (client == null) {
			client = AmazonDynamoDBClientBuilder.defaultClient();
		}
		return client;
	}
}
