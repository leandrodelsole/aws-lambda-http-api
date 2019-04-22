package br.com.lds.aws.lambda.http.api.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.lds.aws.lambda.http.api.dto.UserRequest;
import br.com.lds.aws.lambda.http.api.model.User;
import br.com.lds.aws.lambda.http.api.service.UserService;

public class InsertUserHandler implements RequestHandler<UserRequest, User> {

	private UserService userService = new UserService();

	public User handleRequest(UserRequest userRequest, Context context) {

		final LambdaLogger logger = context.getLogger();
		logger.log("\nrequest " + userRequest);

		final User user = new User(userRequest);
		userService.create(user);
		return user;
	}
}
