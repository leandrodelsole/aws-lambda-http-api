package br.com.lds.aws.lambda.http.api.service;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import br.com.lds.aws.lambda.http.api.model.User;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

public class UserService {

	private static final String TABLE = "User";

	private final Context context;
	private final DynamoService dynamoService;

	public UserService(Context context) {
		this.context = context;
		dynamoService = new DynamoService(context);
	}

	public void create(User user) {
		final PutItemRequest putItemRequest = PutItemRequest.builder()
				.tableName(TABLE)
				.item(itemOf(user))
				.build();
		dynamoService.getClient().putItem(putItemRequest);
	}

	public List<User> list() {
		return dynamoService.getClient()
				.scan(ScanRequest.builder().tableName(TABLE).build())
				.items().stream()
				.map(this::userOf)
				.collect(toList());
	}

	private User userOf(Map<String, AttributeValue> item) {
		return new User(item.get("id").toString(), item.get("name").toString(), item.get("email").toString());
	}

	private Map<String, AttributeValue> itemOf(User user) {
		final Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", AttributeValue.builder().s(user.getId()).build());
		item.put("name", AttributeValue.builder().s(user.getName()).build());
		item.put("email", AttributeValue.builder().s(user.getEmail()).build());
		return item;
	}
}
