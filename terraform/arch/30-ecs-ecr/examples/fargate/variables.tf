variable "context" {
  type = object({
    aws_credentials_file    = string # describe a path to locate a credentials from access aws cli
    aws_profile             = string # describe a specifc profile to access a aws cli
    aws_region              = string # describe default region to create a resource from aws
    region_alias            = string # region alias or AWS
    project                 = string # project name is usally account's project name or platform name
    environment             = string # Runtime Environment such as develop, stage, production
    env_alias               = string # Runtime Environment such as develop, stage, production
    owner                   = string # project owner
    team                    = string # Team name of Devops Transformation
    generator_date          = string # generator_date
    domain                  = string # public domain name (ex, tools.customer.co.kr)
    pri_domain              = string # private domain name (ex: toolchain)
    #access_key              = string
    #secret_key              = string
  })
}


locals {
  name_prefix               = format("%s-%s%s", var.context.project, var.context.region_alias, var.context.env_alias)
  cluster_name = format("%s-ecs", local.name_prefix)
  tags = {
    Project     = var.context.project
    Environment = var.context.environment
    Team        = var.context.team
    Owner       = var.context.owner
    Name        =  local.cluster_name
    Repository  = "https://github.com/terraform-aws-modules/terraform-aws-ecs"
  }
}

