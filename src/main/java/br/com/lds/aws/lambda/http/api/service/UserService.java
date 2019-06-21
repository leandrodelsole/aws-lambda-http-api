package br.com.lds.aws.lambda.http.api.service;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.lambda.runtime.Context;

import br.com.lds.aws.lambda.http.api.model.User;

public class UserService {

	private static final String TABLE = "User";

	private final Context context;
	private final DynamoService dynamoService;

	public UserService(Context context) {
		this.context = context;
		dynamoService = new DynamoService(context);
	}

	public void create(User user) {
		final PutItemRequest putItemRequest = new PutItemRequest(TABLE, itemOf(user));
		dynamoService.getClient().putItem(putItemRequest);
	}

	public List<User> list() {
		return dynamoService.getClient()
				.scan(new ScanRequest(TABLE))
				.getItems().stream()
				.map(this::userOf)
				.collect(toList());
	}

	private User userOf(Map<String, AttributeValue> item) {
		return new User(item.get("id").getS(), item.get("name").getS(), item.get("email").getS());
	}

	private Map<String, AttributeValue> itemOf(User user) {
		final Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", new AttributeValue(user.getId()));
		item.put("name", new AttributeValue(user.getName()));
		item.put("email", new AttributeValue(user.getEmail()));
		return item;
	}
}
