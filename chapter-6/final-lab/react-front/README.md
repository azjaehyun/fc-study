# react-front 프론트 입니다.

# react-front ecs task 실행하기 전에 선행작업
## 1. 역할 생성 - task 등록시 필요.
 -  선행 작업
 > -  ecs-task-rule 이름으로 역할생성
 > - 보안자격증명 > 역활 탭 이동 > 역할 만들기 > Aws Service > 맨밑 다른 aws 서비스의 사용 사례 클릭 후 >
 >  Elastic Container Service > Elastic Conainer Service Task 로 생성 -  아래 권한 2개 넣고 생성
 > -  CloudWatchLogsFullAccess	
 > -  AmazonEC2ContainerRegistryFullAccess
---
## 2. 해당 상위 경로에서 iam 폴더로 이동 후 README.md 가이드 대로 ecs-cicd-deploy 역할 생성 - jenkins pipeline 에서 withAWS 명령어 사용하기 위해서 필요
---

## 3. application-back-service.json 등록법 
 -  task 등록하기 전에 해당 json 파일에서 사용자 ARN을 변경해주세요.
---
## 4. 프로젝트의 빌드 및 실행 방법은 아래와 같습니다.

### 시스템 요구 사항 - version info
```
yarn version v1.22.19
node version v16.20.1
npm v8.19.4
```

### install & build 택1
```
yarn install; yarn build;
&
npm install; npm run build;
```

### run start 택1
```
rpm run start;
&
yarn start;
```


### docker build
```
docker build -t react-front:v1 .
```




