
# Set up CloudWatch group and log stream and retain logs for 30 days
resource "aws_cloudwatch_log_group" "mutant_log_group" {
  name              = "/ecs/mutant-app"
  retention_in_days = 30

  tags = {
    Name = "mutant-log-group"
  }
}

resource "aws_cloudwatch_log_stream" "mutant_log_stream" {
  name           = "mutant-log-stream"
  log_group_name = aws_cloudwatch_log_group.mutant_log_group.name
}