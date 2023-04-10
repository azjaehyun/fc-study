resource "aws_internet_gateway" "igw-template" {
  count = var.internet_gateway_enabled ? 1 : 0
  vpc_id = var.vpc_id
  tags = var.tag_name
}