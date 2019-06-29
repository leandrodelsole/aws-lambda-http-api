package br.com.lds.aws.lambda.http.api.service;

import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public final class DynamoService {

	//se fosse spring, transformaríamos em um bean, por exemplo
	private static DynamoDbClient client;
	private final Context context;

	public DynamoService(Context context) {
		this.context = context;
	}

	public DynamoDbClient getClient() {
		context.getLogger().log("\nDynamoService.getClient SDK v2 UrlConnectionHttpClient before " + context.getRemainingTimeInMillis());
		if (client == null) {
			client = DynamoDbClient.builder()
					.httpClient(UrlConnectionHttpClient.create())
					.build();
		}
		context.getLogger().log("\nDynamoService.getClient SDK v2 UrlConnectionHttpClient after " + context.getRemainingTimeInMillis());
		return client;
	}
}
