
# ECS Clusters w/ Fargate

Configuration in this directory creates:

- ECS cluster using Fargate (on-demand and spot) capacity provider

## Usage

To run this example you need to execute:

```bash
$ terraform init
$ terraform plan
$ terraform apply
```

Note that this example may create resources which will incur monetary charges on your AWS bill. Run `terraform destroy` when you no longer need these resources.

<!-- BEGINNING OF PRE-COMMIT-TERRAFORM DOCS HOOK -->
## Requirements

| Name | Version |
|------|---------|
| <a name="requirement_terraform"></a> [terraform](#requirement\_terraform) | >= 1.0 |
| <a name="requirement_aws"></a> [aws](#requirement\_aws) | >= 4.6 |

## Providers

| Name | Version |
|------|---------|
| <a name="provider_aws"></a> [aws](#provider\_aws) | >= 4.6 |

## Modules

| Name | Source | Version |
|------|--------|---------|
| <a name="module_ecs"></a> [ecs](#module\_ecs) | ../.. | n/a |
| <a name="module_ecs_disabled"></a> [ecs\_disabled](#module\_ecs\_disabled) | ../.. | n/a |

## Resources

| Name | Type |
|------|------|
| [aws_cloudwatch_log_group.this](https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/cloudwatch_log_group) | resource |

## Inputs

No inputs.

## Outputs

| Name | Description |
|------|-------------|
| <a name="output_autoscaling_capacity_providers"></a> [autoscaling\_capacity\_providers](#output\_autoscaling\_capacity\_providers) | Map of capacity providers created and their attributes |
| <a name="output_cluster_arn"></a> [cluster\_arn](#output\_cluster\_arn) | ARN that identifies the cluster |
| <a name="output_cluster_capacity_providers"></a> [cluster\_capacity\_providers](#output\_cluster\_capacity\_providers) | Map of cluster capacity providers attributes |
| <a name="output_cluster_id"></a> [cluster\_id](#output\_cluster\_id) | ID that identifies the cluster |
| <a name="output_cluster_name"></a> [cluster\_name](#output\_cluster\_name) | Name that identifies the cluster |
<!-- END OF PRE-COMMIT-TERRAFORM DOCS HOOK -->

# 질문사항
```
1. AWS Cloud Map - aws namespace 이 무엇인지 정확히 파악
2. ecs fargate 용량 공급자 전략이 무엇인지?
3. ecs 배포구성
 - 서비스 & 타스크 다른점
4. alb service 연결
```

# testing - aws cli - https://docs.aws.amazon.com/cli/latest/reference/ecs/create-service.html
```
10264  aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 047675330097.dkr.ecr.ap-northeast-2.amazonaws.com
10265  cd /
10266  cd tmp
10267  ls
10268  mkdir nginxDocker
10269  cd nginxDocker
10270  ls
10271  docker pull nginx
10272  docker images
10273  docker tag nginx:latest 047675330097.dkr.ecr.ap-northeast-2.amazonaws.com/nginxtesting:latest
10274  docker images
10275  docker push 047675330097.dkr.ecr.ap-northeast-2.amazonaws.com/nginxtesting:latest
10276  cd /
10277  cd tmp
10278  ls


#memo
task name
ecr-nginx-test-task\

service
ecr-nginx-test-service

nlb
ecr-nginx-test-alb

nlb targetgrp
ecr-nginx-test-alb-tgp


ecs fargate run 
aws ecs create-service --cluster terraformPrac-an2p-ecs --service-name ecr-nginx-test-service-jenkins --task-definition ecs-fargate-service:1 --desired-count 1 --launch-type "FARGATE" --network-configuration "awsvpcConfiguration={subnets=[subnet-0d5f06ce2fec2df02],securityGroups=[sg-04cc54e2cc38f3437]}"
```

aws ecs create-service --cluster terrafㅁormPrac-an2p-ecs --service-name ecr-nginx-test-service-jenkins --task-definition ecs-fargate-service:1 --desired-count 1 --deployment-controller type=EXTERNAL --launch-type "FARGATE" --network-configuration "awsvpcConfiguration={subnets=[subnet-0d5f06ce2fec2df02],securityGroups=[sg-04cc54e2cc38f3437]}" 
// --deployment-controller type=EXTERNAL 추가