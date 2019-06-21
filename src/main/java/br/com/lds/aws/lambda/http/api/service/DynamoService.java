package br.com.lds.aws.lambda.http.api.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;

public final class DynamoService {

	//se fosse spring, transformaríamos em um bean, por exemplo
	private static AmazonDynamoDB client;
	private final Context context;

	public DynamoService(Context context) {
		this.context = context;
	}

	public AmazonDynamoDB getClient() {
		context.getLogger().log("\nDynamoService.getClient before " + context.getRemainingTimeInMillis());
		if (client == null) {
			client = AmazonDynamoDBClientBuilder.defaultClient();
		}
		context.getLogger().log("\nDynamoService.getClient after " + context.getRemainingTimeInMillis());
		return client;
	}
}
