package br.com.lds.aws.lambda.http.api.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.lds.aws.lambda.http.api.dto.UserRequest;
import br.com.lds.aws.lambda.http.api.model.User;
import br.com.lds.aws.lambda.http.api.service.UserService;

public class InsertUserHandler implements RequestHandler<UserRequest, User> {

	public User handleRequest(UserRequest userRequest, Context context) {

		final LambdaLogger logger = context.getLogger();
		logger.log("\nhandleRequest start " + userRequest + " remaining time " + context.getRemainingTimeInMillis());

		final User user = new User(userRequest);
		new UserService(context).create(user);

		logger.log("\nhandleRequest end remaining time " + context.getRemainingTimeInMillis());
		return user;
	}
}
