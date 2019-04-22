package br.com.lds.aws.lambda.http.api.dto;

public class UserRequest {
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserRequest{" +
				"name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
