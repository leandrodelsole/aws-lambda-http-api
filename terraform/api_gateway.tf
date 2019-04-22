resource "aws_api_gateway_rest_api" "http-api" {
  name        = "AWS Lambda HTTP API"
  description = "API HTTP utilizando Lambda AWS"
}

resource "aws_api_gateway_resource" "users" {
  rest_api_id = "${aws_api_gateway_rest_api.http-api.id}"
  parent_id   = "${aws_api_gateway_rest_api.http-api.root_resource_id}"
  path_part   = "users"
}

resource "aws_api_gateway_method" "users-insert" {
  rest_api_id   = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id   = "${aws_api_gateway_resource.users.id}"
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_method" "users-list" {
  rest_api_id   = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id   = "${aws_api_gateway_resource.users.id}"
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "users-insert" {
  rest_api_id             = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id             = "${aws_api_gateway_resource.users.id}"
  http_method             = "${aws_api_gateway_method.users-insert.http_method}"
  integration_http_method = "POST" #must always be post when invoking lambda
  type                    = "AWS"
  uri                     = "arn:aws:apigateway:${var.region}:lambda:path/2015-03-31/functions/arn:aws:lambda:${var.region}:${var.account_id}:function:${aws_lambda_function.insert-user.function_name}/invocations"
  credentials             = "arn:aws:iam::${var.account_id}:role/${aws_iam_role.insert-user-exec.name}"
}

resource "aws_api_gateway_integration" "users-list" {
  rest_api_id             = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id             = "${aws_api_gateway_resource.users.id}"
  http_method             = "${aws_api_gateway_method.users-list.http_method}"
  integration_http_method = "POST" #must always be post when invoking lambda
  type                    = "AWS"
  uri                     = "arn:aws:apigateway:${var.region}:lambda:path/2015-03-31/functions/arn:aws:lambda:${var.region}:${var.account_id}:function:${aws_lambda_function.list-user.function_name}/invocations"
  credentials             = "arn:aws:iam::${var.account_id}:role/${aws_iam_role.insert-user-exec.name}"
}

resource "aws_api_gateway_method_response" "insert-200" {
  rest_api_id = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id = "${aws_api_gateway_resource.users.id}"
  http_method = "${aws_api_gateway_method.users-insert.http_method}"
  response_models = {
    "application/json" = "Empty"
  }
  status_code = "200"
}

resource "aws_api_gateway_method_response" "list-200" {
  rest_api_id = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id = "${aws_api_gateway_resource.users.id}"
  http_method = "${aws_api_gateway_method.users-list.http_method}"
  response_models = {
    "application/json" = "Empty"
  }
  status_code = "200"
}

resource "aws_api_gateway_integration_response" "users-insert" {
  depends_on  = ["aws_api_gateway_integration.users-insert"]
  rest_api_id = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id = "${aws_api_gateway_resource.users.id}"
  http_method = "${aws_api_gateway_method.users-insert.http_method}"
  status_code = "${aws_api_gateway_method_response.insert-200.status_code}"
}

resource "aws_api_gateway_integration_response" "users-list" {
  depends_on  = ["aws_api_gateway_integration.users-list"]
  rest_api_id = "${aws_api_gateway_rest_api.http-api.id}"
  resource_id = "${aws_api_gateway_resource.users.id}"
  http_method = "${aws_api_gateway_method.users-list.http_method}"
  status_code = "${aws_api_gateway_method_response.list-200.status_code}"
}

resource "aws_api_gateway_deployment" "http-api" {
  depends_on = [
    "aws_api_gateway_integration.users-insert", "aws_api_gateway_integration.users-list"]
  stage_name = "${var.api_env_stage_name}"
  rest_api_id = "${aws_api_gateway_rest_api.http-api.id}"

}

output "curl_list_users" {
  value = "curl -H 'Content-Type: application/json' -X GET ${aws_api_gateway_deployment.http-api.invoke_url}/${aws_api_gateway_resource.users.path_part}/ ; echo"
}

output "curl_insert_user" {
  value = "curl -H 'Content-Type: application/json' -d '{\"name\": \"Teste\", \"email\": \"teste@teste.com\"}' -X POST ${aws_api_gateway_deployment.http-api.invoke_url}/${aws_api_gateway_resource.users.path_part}/ ; echo"
}