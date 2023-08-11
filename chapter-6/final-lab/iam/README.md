### ecs fargate 에서 ci cd 배포 라인을 위해서 필요한 role 정책을 만듭니다.
```
Identity and Access Management(IAM)에서 ci cd 배포 라인을 위해서 필요한 role 정책을 만듭니다.
policy 폴더와 role 폴더의 가이드를 받으세요.
```

## 1. 역할 생성 - ecs task 등록시 필요. 
- ecs-task-rule 이름으로 역할생성
- 보안자격증명 >
     > 역활 탭 이동 > 오른쪽 역할 만들기 버튼 클릭
     > Aws 서비스 > 맨밑 다른 aws 서비스의 사용 사례 클릭 후 
     > Elastic Container Service > Elastic Conainer Service Task 로 생성 

- 아래 권한 2개 넣고 생성
     > CloudWatchLogsFullAccess  
     > AmazonEC2ContainerRegistryFullAccess  
---
---
## 2. 역할 생성 - jenkins pipeline withAWS 메소드 에서 사용 합니다.
- ecs-cicd-deploy 이름으로 역할 생성
- 보안자격증명 >
   > 역활 탭 이동   >  오른쪽 역할 만들기 클릭 
   > AWS 계정 > 다음 버튼 클릭
- policy cicd-ecr.json add // 파일위치는 해당 경로의 policy 폴더 참조
- policy cicd-ecs.json add // 파일위치는 해당 경로의 policy 폴더 참조
- CloudWatchFullAccess  add //	AWS 관리형	
- AmazonECS_FullAccess add  // AWS 관리형	
- Amazonecs-task-rulePolicy //  AWS 관리형	
  
위 5개 권한을 넣고 ecs-cicd-deploy 이름으로 역할 생성