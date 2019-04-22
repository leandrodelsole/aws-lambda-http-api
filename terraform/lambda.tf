variable "source-code-file" {
  default = "../target/aws-lambda-http-api-1.0-SNAPSHOT.jar"
}

#insert users
resource "aws_lambda_function" "insert-user" {
  function_name = "InsertUserHttpApi"

  filename      = "${var.source-code-file}"
  source_code_hash = "${base64sha256(file(var.source-code-file))}"

  handler = "br.com.lds.aws.lambda.http.api.controller.InsertUserHandler"
  runtime = "java8"

  timeout = 60 #segundos
  memory_size = 256 #mb

  role = "${aws_iam_role.insert-user-exec.arn}"
}

resource "aws_iam_role" "insert-user-exec" {
  name = "insert-user-exec"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": ["apigateway.amazonaws.com","lambda.amazonaws.com"]
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "insert-user" {
  name = "lambda_policy"
  role = "${aws_iam_role.insert-user-exec.id}"

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "lambda:InvokeFunction"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "cloudwatch:Describe*",
        "cloudwatch:Get*",
        "cloudwatch:List*"
      ],
      "Resource": "*"
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "insert-user-DynamoDBAccess" {
  role       = "${aws_iam_role.insert-user-exec.name}"
  policy_arn = "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess"
}

resource "aws_lambda_permission" "insert-user" {
  statement_id  = "AllowAPIGatewayInvokeInsertUser"
  action        = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.insert-user.arn}"
  principal     = "apigateway.amazonaws.com"

  source_arn = "${aws_api_gateway_deployment.http-api.execution_arn}/${aws_api_gateway_integration.users-insert.integration_http_method}${aws_api_gateway_resource.users.path}"
}


#list users
resource "aws_lambda_function" "list-user" {
  function_name = "ListUserHttpApi"

  filename      = "${var.source-code-file}"
  source_code_hash = "${base64sha256(file(var.source-code-file))}"

  handler = "br.com.lds.aws.lambda.http.api.controller.ListUserHandler"
  runtime = "java8"

  timeout = 60 #segundos
  memory_size = 256 #mb

  role = "${aws_iam_role.list-user-exec.arn}"
}

resource "aws_iam_role" "list-user-exec" {
  name = "list-user-exec"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": ["apigateway.amazonaws.com","lambda.amazonaws.com"]
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "list-user" {
  name = "lambda_policy"
  role = "${aws_iam_role.list-user-exec.id}"

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "lambda:InvokeFunction"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "cloudwatch:Describe*",
        "cloudwatch:Get*",
        "cloudwatch:List*"
      ],
      "Resource": "*"
    }
  ]
}
POLICY
}

resource "aws_iam_role_policy_attachment" "list-userDynamoDBAccess" {
  role       = "${aws_iam_role.list-user-exec.name}"
  policy_arn = "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess"
}

resource "aws_lambda_permission" "list-user" {
  statement_id  = "AllowAPIGatewayInvokeListUser"
  action        = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.list-user.arn}"
  principal     = "apigateway.amazonaws.com"

  source_arn = "${aws_api_gateway_deployment.http-api.execution_arn}/${aws_api_gateway_integration.users-list.integration_http_method}${aws_api_gateway_resource.users.path}"
}
