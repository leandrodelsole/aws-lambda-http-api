package br.com.lds.aws.lambda.http.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class EchoHandler implements RequestStreamHandler {

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		final LambdaLogger logger = context.getLogger();
		for (String envVar : new String[]{"_HANDLER", "AWS_REGION", "AWS_EXECUTION_ENV", "AWS_LAMBDA_FUNCTION_NAME", "AWS_LAMBDA_FUNCTION_MEMORY_SIZE", "AWS_LAMBDA_FUNCTION_VERSION", "AWS_LAMBDA_LOG_GROUP_NAME", "AWS_LAMBDA_LOG_STREAM_NAME", "AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY", "AWS_SESSION_TOKEN", "LANG", "TZ", "LAMBDA_TASK_ROOT", "LAMBDA_RUNTIME_DIR", "PATH", "LD_LIBRARY_PATH", "NODE_PATH", "PYTHONPATH", "GEM_PATH", "AWS_LAMBDA_RUNTIME_API"}) {
			logger.log("\n envVar " + envVar + " = " + System.getenv(envVar));
		}

		output.write("echoes".getBytes());
	}
}
