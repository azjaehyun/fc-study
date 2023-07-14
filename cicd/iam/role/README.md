- policy cicd-ecr.json add
- policy cicd-ecs.json add
- CloudWatchFullAccess  add //	AWS 관리형	
- AmazonECS_FullAccess add  // AWS 관리형	
- AmazonECSTaskExecutionRolePolicy //  AWS 관리형	
위 5개 권한을 넣고 ecs-cicd-deploy 이름으로 역할 생성

ecsTaskExecutionRole 	AWS 서비스: ecs-tasks

역할 만들기
aws 서비스 클릭후
밑에 메뉴에 사용사례에서
elastic container service 검색 후
elastic conainer service task 선택후 다음
1.CloudWatchLogsFullAccess
2.AmazonEC2ContainerRegistryFullAccess
2개 권한 넣어주기.


{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "ec2:DescribeTags",
                "ecs:CreateCluster",
                "ecs:DeregisterContainerInstance",
                "ecs:DiscoverPollEndpoint",
                "ecs:Poll",
                "ecs:RegisterContainerInstance",
                "ecs:StartTelemetrySession",
                "ecs:UpdateContainerInstancesState",
                "ecs:Submit*",
                "ecr:GetAuthorizationToken",
                "ecr:BatchCheckLayerAvailability",
                "ecr:GetDownloadUrlForLayer",
                "ecr:BatchGetImage",
                "logs:CreateLogStream",
                "logs:PutLogEvents"
            ],
            "Resource": "*"
        },
        {
            "Effect": "Allow",
            "Action": "ecs:TagResource",
            "Resource": "*",
            "Condition": {
                "StringEquals": {
                    "ecs:CreateAction": [
                        "CreateCluster",
                        "RegisterContainerInstance"
                    ]
                }
            }
        }
    ]
}