### 아래 5개 권한을 넣고 ecs-cicd-deploy 이름으로 역할 생성
```
- cicd-ecr // cicd-ecr.json 파일로 만든 룰 - 경로 ./policy/cicd-ecr.json
- cicd-ecs // cicd-ecs.json 파일로 만든 룰 - 경로 ./policy/cicd-ecs.json
- CloudWatchFullAccess  add //	AWS 관리형	
- AmazonECS_FullAccess add  // AWS 관리형	
- AmazonECSTaskExecutionRolePolicy //  AWS 관리형	
```
