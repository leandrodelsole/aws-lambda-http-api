variable "lib-file" {
  default = "../target/aws-lambda-http-api-lib.zip"
}

resource "aws_lambda_layer_version" "libs" {
  filename      = "${var.lib-file}"
  source_code_hash = "${base64sha256(file(var.lib-file))}"
  layer_name = "UsersLib"

  compatible_runtimes = ["provided"]
}