
data "aws_vpc" "this" {
  filter {
    name   = "tag:Name"
    values = ["${local.name_prefix}-vpc"]
  }
}

data "aws_subnets" "subnets" {
  
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.this.id]
  }
  filter {
    name   = "tag:kubernetes.io/cluster/${local.cluster_name}"
    values = ["shared"]
  }
}

data "aws_subnets" "public" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.this.id]
  }
  filter {
    name   = "tag:kubernetes.io/role/elb"
    values = ["1"]
  }
}

data "aws_subnets" "private" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.this.id]
  }
  filter {
    name   = "tag:kubernetes.io/role/internal-elb"
    values = ["1"]
  }
}

data "aws_subnets" "workers" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.this.id]
  }
  filter {
    name   = "tag:kubernetes.io/role/internal-elb"
    values = ["1"]
  }

  filter {
    name   = "tag:Project"
    values = [ var.context.project ]
  }

}

data "aws_caller_identity" "current" {}
data "aws_partition" "current" {}
