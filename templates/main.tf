resource "aws_s3_bucket" "infra_db_data_bucket" {
  bucket = "my-db-data-bucket"
  acl    = "private"

  tags = {
    Name        = "MyMutantBucket"
    Environment = "prod"
  }
}
