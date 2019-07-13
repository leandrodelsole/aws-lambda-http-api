package br.com.lds.aws.lambda.http.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lds.aws.lambda.http.api.model.User;
import br.com.lds.aws.lambda.http.api.service.UserService;

public class ListUserHandler implements RequestStreamHandler {

	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		final List<User> result = new UserService(context).list();
		objectMapper.writeValue(output, result);
	}
}
