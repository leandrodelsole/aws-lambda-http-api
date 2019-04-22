package br.com.lds.aws.lambda.http.api.controller;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.lds.aws.lambda.http.api.model.User;
import br.com.lds.aws.lambda.http.api.service.UserService;

public class ListUserHandler implements RequestHandler<Void, List<User>> {

	private UserService userService = new UserService();

	public List<User> handleRequest(Void voidRequest, Context context) {
		return userService.list();
	}
}
