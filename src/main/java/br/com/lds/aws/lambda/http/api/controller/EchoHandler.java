package br.com.lds.aws.lambda.http.api.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.lds.aws.lambda.http.api.dto.UserRequest;

public class EchoHandler implements RequestHandler<UserRequest, UserRequest> {
	@Override
	public UserRequest handleRequest(UserRequest input, Context context) {
		return input;
	}
}
