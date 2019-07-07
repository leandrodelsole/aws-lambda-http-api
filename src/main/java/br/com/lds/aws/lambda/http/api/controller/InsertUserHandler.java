package br.com.lds.aws.lambda.http.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lds.aws.lambda.http.api.dto.UserRequest;
import br.com.lds.aws.lambda.http.api.model.User;
import br.com.lds.aws.lambda.http.api.service.UserService;

public class InsertUserHandler implements RequestStreamHandler {

	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		final ObjectMapper objectMapper = new ObjectMapper();
		final LambdaLogger logger = context.getLogger();
		final UserRequest userRequest = objectMapper.readValue(input, UserRequest.class);

		logger.log("\nhandleRequest start " + userRequest + " remaining time " + context.getRemainingTimeInMillis());

		final User user = new User(userRequest);
		new UserService(context).create(user);

		logger.log("\nhandleRequest end remaining time " + context.getRemainingTimeInMillis());

		objectMapper.writeValue(output, user);
	}
}
