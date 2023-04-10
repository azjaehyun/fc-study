
### vpc에 internet gateway 적용
#### 모듈에서 다음과 같이 호출 
* #### sample aws_internet_gateway
```
module "aws_vpc_network" {
  source    = "../../../../modules/aws/network/igw_nat_subnet"
  internet_gateway_enabled = true  // internet_gateway_enabled 사용 여부 condition
  vpc_id    = module.aws_vpc.vpc_id
  subnet_id = module.aws_public_subnet_a.subnet_id
  tag_name = merge(local.tags, {Name = format("%s-igw-nat-sunet", local.name_prefix)})
}
```

```
igw.tf // vpc에 인터넷 게이트 웨이를 붙일지 안붙일지 결정.
resource "aws_internet_gateway" "igw-template" {
  count = var.internet_gateway_enabled ? 1 : 0 // count가 1이면 실행 0이면 해당 resource는 패스
  vpc_id = var.vpc_id
  tags = var.tag_name
}
```