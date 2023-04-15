
# ec2 connect
## resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/key_pair
## data source : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/key_pair
module "aws_key_pair" {
  source = "../../modules/aws/keypair"
  keypair_name   = var.keypair_name
  tag_name = merge(local.tags, {Name = format("%s-key", local.name_prefix)})
}

# vpc setting
## resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/vpc
## data source : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/vpc
module "aws_vpc" {
  source     = "../../modules/aws/vpc"
  cidr_block = "${var.vpc_cidr}.0.0/16"
  tag_name = merge(local.tags, {Name = format("%s-vpc", local.name_prefix)})
}

# https://www.devc4sh.com/11 vpc internet gateway enable 참조
# internet gateway & NAT & subnet Network
# resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/internet_gateway
# data source : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/internet_gateway
# 응용 리소스 : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/internet_gateway_attachment
module "aws_vpc_network" {
  source    = "../../modules/aws/network/igw_nat_subnet"
  internet_gateway_enabled = true
  vpc_id    = module.aws_vpc.vpc_id
  subnet_id = module.aws_public_subnet_a.subnet_id
  tag_name = merge(local.tags, {Name = format("%s-igw-nat-sunet", local.name_prefix)})
}

#
module "aws_public_subnet_a" { 
  source     = "../../modules/aws/subnet"
  cidr_block = "${var.vpc_cidr}.11.0/24"
  vpc_id     = module.aws_vpc.vpc_id
  is_public  = true
  availability_zone = "${var.context.aws_region}a"
  tag_name = merge(local.tags, {
    Name = format("%s-subnet-public-a", local.name_prefix)
    "kubernetes.io/cluster/${local.name_prefix}-eks" = "shared"
    "kubernetes.io/role/elb" = 1
  })
}


# public subnet setting - [ availability_zone_c ] - 예비 
module "aws_public_subnet_c" {
  source     = "../../modules/aws/subnet"
  cidr_block = "${var.vpc_cidr}.12.0/24"
  vpc_id     = module.aws_vpc.vpc_id
  is_public  = true
  availability_zone = "${var.context.aws_region}c"
  tag_name = merge(local.tags, {Name = format("%s-subnet-public-c", local.name_prefix)
  "kubernetes.io/cluster/${local.name_prefix}-eks" = "shared"
  "kubernetes.io/role/elb" = 1
  })
}


# private subnet setting - [ availability_zone_a ] - private1
module "aws_private_subnet_a" {
  source     = "../../modules/aws/subnet"
  cidr_block = "${var.vpc_cidr}.1.0/24"
  vpc_id     = module.aws_vpc.vpc_id
  is_public  = false
  availability_zone = "${var.context.aws_region}a"
  tag_name = merge(local.tags, {Name = format("%s-subnet-private-a", local.name_prefix)
  "kubernetes.io/cluster/${local.name_prefix}-eks" = "shared"
  "kubernetes.io/role/internal-elb" = 1
  })
}

# private subnet setting - [ availability_zone_c ] - private2
module "aws_private_subnet_c" {
  source     = "../../modules/aws/subnet"
  cidr_block = "${var.vpc_cidr}.2.0/24"
  vpc_id     = module.aws_vpc.vpc_id
  is_public  = false
  availability_zone = "${var.context.aws_region}c"
  tag_name = merge(local.tags, {Name = format("%s-subnet-private-c", local.name_prefix)
  "kubernetes.io/cluster/${local.name_prefix}-eks" = "shared"
  "kubernetes.io/role/internal-elb" = 1
  })
}

# public route setting - [ internetgateway route table ]
# resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/route_table
# data source : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/route_table
resource "aws_route_table" "public-route" {
  vpc_id = module.aws_vpc.vpc_id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = module.aws_vpc_network.igw_id
  }
  tags = merge(local.tags, {Name = format("%s-public-route", local.name_prefix)})
}

# resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/route_table_association
resource "aws_route_table_association" "to-public-a" {
  subnet_id      = module.aws_public_subnet_a.subnet_id
  route_table_id = aws_route_table.public-route.id
}


resource "aws_route_table_association" "to-public-c" {
  subnet_id      = module.aws_public_subnet_c.subnet_id
  route_table_id = aws_route_table.public-route.id
}


# private route setting
resource "aws_route_table" "private-route" {
  vpc_id = module.aws_vpc.vpc_id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = module.aws_vpc_network.nat_gateway_id
  }
  tags = merge(local.tags, {Name = format("%s-private-route", local.name_prefix)})
}

resource "aws_route_table_association" "to-private-web-a" {
  subnet_id      = module.aws_private_subnet_a.subnet_id
  route_table_id = aws_route_table.private-route.id
}

resource "aws_route_table_association" "to-private-web-c" {
  subnet_id      = module.aws_private_subnet_c.subnet_id
  route_table_id = aws_route_table.private-route.id
}


# resource : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/security_group
# data source : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/data-sources/security_group
module "aws_sg_was" {
  source = "../../modules/aws/security/was"
  vpc_id = module.aws_vpc.vpc_id
  name =  format("%s-sg-ssh-default", local.name_prefix)
  tag_name = merge(local.tags, {Name = format("%s-sg-default", local.name_prefix)})
}



# docker로 jenkins 실행시키는 모듈
module "aws_ec2_public_jenkins_ec2" {
  source        = "../../modules/aws/ec2/docker_jenkins"
  sg_groups     = [module.aws_sg_was.sg_id]
  key_name      = module.aws_key_pair.key_name
  public_access = true
  subnet_id     = module.aws_public_subnet_a.subnet_id

  docker_image = "symjaehyun/jenkins:latest"   // specific docker image name
  in_port      = "8080"    // specific port
  out_port     = "8080"    // specific port
  key_path     = "./${module.aws_key_pair.key_name}.pem"
  tag_name     = merge(local.tags, {Name = format("%s-ec2-jenkins", local.name_prefix)})
  
 } 


#module "aws_ec2_bastion" {
#  source        = "../../modules/aws/ec2/ec2_bastion"
#  sg_groups     = [module.aws_sg_default.sg_id]
#  key_name      = module.aws_key_pair.key_name
#  public_access = true
#  subnet_id     = module.aws_public_subnet_a.subnet_id
#  tag_name = merge(local.tags, {Name = format("%s-ec2-public-bastion-a", local.name_prefix)})
#}
