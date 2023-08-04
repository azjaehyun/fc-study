- policy cicd-ecr.json add
- policy cicd-ecs.json add
- CloudWatchFullAccess  add //	AWS 관리형	
- AmazonECS_FullAccess add  // AWS 관리형	
- Amazonecs-task-rulePolicy //  AWS 관리형	
위 5개 권한을 넣고 ecs-cicd-deploy 이름으로 역할 생성