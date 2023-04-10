# resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/eip
resource "aws_eip" "eip-template" {
  vpc = true
  lifecycle {
    create_before_destroy = true
  }
}


# 프라이빗 서브넷에서 외부 인터넷으로 요청을 내보낼 수 있도록 하는 NAT 게이트웨이
# resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/nat_gateway
# data source : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/nat_gateway
# 참고 자료 : https://blog.outsider.ne.kr/1301
resource "aws_nat_gateway" "ngw-template" {
  allocation_id = aws_eip.eip-template.id
  subnet_id     = var.subnet_id
  tags = var.tag_name
}