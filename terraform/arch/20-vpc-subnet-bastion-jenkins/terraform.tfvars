context = {
    aws_credentials_file    = "$HOME/.aws/credentials"
    aws_profile             = "jaehyun.yang@bespinglobal.com"
    aws_region              = "us-east-2"
    region_alias            = "ue2"

    project                 = "devops-mvp"
    environment             = "dev"
    env_alias               = "d"
    owner                   = "jaehyun.yang@bespinglobal.com"
    team_name               = "fastcamp-devops"
    team                    = "devops"
    generator_date          = "20230409"
}

# vpc prefix ip
vpc_cidr = "40.40"
keypair_name = "fastcamp-prac-key"
