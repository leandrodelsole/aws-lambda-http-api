resource "aws_dynamodb_table" "user" {
  name           = "User"
  billing_mode   = "PAY_PER_REQUEST"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }
}
