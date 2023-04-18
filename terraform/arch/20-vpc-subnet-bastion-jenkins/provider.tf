# my aws key setting 
## doc : https://registry.terraform.io/providers/hashicorp/aws/latest/docs
provider "aws" { 
  region      = var.context.aws_region
  profile     = var.context.aws_profile
  # shared_config_files      = ["/Users/tf_user/.aws/conf"]
  shared_credentials_files = [var.context.aws_credentials_file]
}
