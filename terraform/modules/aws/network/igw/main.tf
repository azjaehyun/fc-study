resource "aws_internet_gateway" "igw-template" {
  vpc_id = var.vpc_id
  tags = var.tag_name
}