package br.com.lds.aws.lambda.http.api.model;

import java.util.Objects;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;

import br.com.lds.aws.lambda.http.api.dto.UserRequest;

public class User {
	private final String id;
	private final String name;
	private final String email;

	public User(String id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public User(UserRequest userRequest) {
		this(UUID.randomUUID().toString(), userRequest.getName(), userRequest.getEmail());
	}

	public User(Item item) {
		this.id = item.getString("id");
		this.name = item.getString("name");
		this.email = item.getString("email");
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		final User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
