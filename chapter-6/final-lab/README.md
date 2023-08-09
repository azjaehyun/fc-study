## 작업 순서

### Clip 3 - MVP 프로젝트 운영을 위한 클라우드 인프라 구축

 1. vpc & subnet 생성
>   - prefix name : final-lab
 2. security 그룹생성
>   - http - 80 port : http-scr-grp
>   - ssh - 22 port : ssh-scr-grp
>   - mysql  - 3306 port : mysql-scr-grp
>   - springboot 
>      - 8080 , 8888 port : spring-scr-grp
 3. rds subnet 생성
>   - final-db-subnet : private a , c zone
 4. rds mysql instance 생성
      - instance name : final-db-mysql
      - master name : admin 
      - password : adminadmin
      - 추가구성 - 기초 데이터 베이스 이름 : application
      - mysql console 접속
       / 콘솔 접속 명령어
       >   mysql -h {rds_endpoint_address} -p application 
       >> 패스워드는 rds에서 생성시 패스워드 입력
      - [db user 및 권한 생성](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/mysql/setup.sql)




--- 
---

### Clip 4 - DevOps 구성을 위한 GIT + JENKINS  서버 구성
public A zone에 Ec2 생성 - jenkins 설치 
 - instance name : final-jenkins
1. yum 관련 패키지 설치
>   - docker 설치 
>   - yum install docker -y
>   - systemctl enable --now docker
>  - systemdtl status docker
>  - usermod -aG docker ec2-user
>   - docker version
2. mysql 설치  
>   - yum install mysql -y
>   - mysql admin console test 
>> - mysql -h database-1.csekmcxp0nqq.ap-northeast-2.rds.amazonaws.com -u admin -p application      
>> 접속후 >> [db user 및 권한 생성](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/mysql/setup.sql)
3.  docker jenkins 설치
>   -  docker run -d -p 80:8080 symjaehyun/jenkins:latest
---
---

     






### Clip 5 - DevOps 구성을 위한 Jenkins CI/CD 빌드 배포라인 구성
- 파이프 라인 코드 링크
> - [react-front pipeline](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/jenkins-pipeline-script/pipeline-final-react-front)
> - [applicaton-back pipeline](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/jenkins-pipeline-script/pipeline-final-application-back)
> - [jobposting-back pipline](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/jenkins-pipeline-script/pipeline-final-jobposting-back)

- Dockerfile 링크
> - [react-front Dockerfile](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/react-front/Dockerfile)
> - [applicaton-back Dockerfile](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/application-back/Dockerfile)
> - [applicaton-back Dockerfile-Multi](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/application-back/Dockerfile-Multi)
> - [jobposting-back Dockerfile](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/jobposting-back/Dockerfile)
> - [jobposting-back Dockerfile-Multi](https://github.com/azjaehyun/fc-study/blob/main/chapter-6/final-lab/jobposting-back/Dockerfile-Multi)

- 파이프라인 job(item) 복사시 왼쪽 메뉴 > 새로운 item > 가장 밑에 메뉴 Copy from 이용.

---
---
### Clip 6 - 컨테이너 기반의 애플리케이션을 ECS Fargate에서 운영
- [application-back guide README.md 참조](https://github.com/azjaehyun/fc-study/tree/main/chapter-6/final-lab/application-back)
- [jobposting-back guide README.md 참조](https://github.com/azjaehyun/fc-study/tree/main/chapter-6/final-lab/jobposting-back)
- [react-front guide README.md 참조](https://github.com/azjaehyun/fc-study/tree/main/chapter-6/final-lab/react-front)
