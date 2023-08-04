## 1. 아래 5개 권한을 넣고 ecs-cicd-deploy 이름으로 역할 생성 - jenkins pipeline withAWS 사용시 사용
- 보안자격증명 > 역활 탭 이동 > 역할 만들기 > Aws 계정으로 생성 
```
- cicd-ecr // cicd-ecr.json 파일로 만든 룰 - 경로 ./policy/cicd-ecr.json
- cicd-ecs // cicd-ecs.json 파일로 만든 룰 - 경로 ./policy/cicd-ecs.json
- CloudWatchFullAccess  add //	AWS 관리형
- AmazonECS_FullAccess add  // AWS 관리형	
- Amazonecs-task-rulePolicy //  AWS 관리형	
```
---
---

## 2. 아래 2개 권한을 넣고 ecs-task-rule 이름으로 역할 생성 
### 역할 생성 - task 등록시 필요.
 -  선행 작업
 > -  ecs-task-rule 이름으로 역할생성
 > - 보안자격증명 > 역활 탭 이동 > 역할 만들기 > Aws Service > 맨밑 다른 aws 서비스의 사용 사례 클릭 후 >
 >  Elastic Container Service > Elastic Conainer Service Task 로 생성 -  아래 권한 2개 넣고 생성
 > -  CloudWatchLogsFullAccess	
 > -  AmazonEC2ContainerRegistryFullAccess
 
### ecs-task-rule 역할은 task 등록시 taskRoleArn , executionRoleArn 에서 사용됩니다.
- 샘플은 아래와 같습니다. sample-task.json 
```
{
  "memory": "2048",
  "cpu": "1024",
  "containerDefinitions": [
    {
      "portMappings": [
        {
          "hostPort": 8080,
          "containerPort": 8080,
          "protocol": "tcp"
        }
      ],
      "essential": true,
      "name": "application-back-service",
      "cpu": 1024,
      "memory": 2048,
      "image": "047675330097.dkr.ecr.ap-northeast-2.amazonaws.com/application-back:1",
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "finallab-staging",
          "awslogs-region": "ap-northeast-2",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ],
  "networkMode": "awsvpc",
  "family": "application-back-service",
  "runtimePlatform": {
    "cpuArchitecture": "X86_64",
    "operatingSystemFamily": "LINUX"
  },
  "requiresCompatibilities": ["FARGATE"],
  "taskRoleArn": "arn:aws:iam::047675330097:role/ecs-cicd-deploy",
  "executionRoleArn": "arn:aws:iam::047675330097:role/ecs-cicd-deploy",
  "tags": [
    {
      "key": "env",
      "value": "dev"
    }
  ]
}

```