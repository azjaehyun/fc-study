### 작업 순서
```
 1. vpc 생성
 2. public A zone Ec2 생성 - jenkins 설치
    yum 관련 패키지 설치
     : docker 설치 
       - yum install docker -y
         systemctl enable --now docker
         systemdtl status docker
         usermod -aG docker ec2-user
         docker version
     : git 설치 
        - yum install git -y
     : mysql 설치  
        - yum install mysql -y
     : java 설치 
        - yum install java-11-amazon-corretto.x86_64 -y
     : node 설치 - # 버전 확인은 https://rpm.nodesource.com 사이트에서 확인 가능
        - curl -sL https://rpm.nodesource.com/setup_16.x | sudo -E bash - 
     : nodejs 신규 설치 
        - yum install -y nodejs 
     : docker jenkins 설치
        - docker run -d -p 80:8080 symjaehyun/jenkins:latest
  3.   
```

##


node 설치
# 버전 확인은 https://rpm.nodesource.com 사이트에서 확인 가능 curl -sL https://rpm.nodesource.com/setup_16.x | sudo -E bash - # nodejs 신규 설치 yum install -y nodejs 



# docker jenkins 설치
- docker run -d -p 80:8080 symjaehyun/jenkins:latest

alb target 생성  - jenkins
alb 생성 - lab6 공통으로 쓸것.




github webhook등록
http://lab-alb-yjh-1038660069.ap-northeast-2.elb.amazonaws.com/github-webhook/
Content type - application/json
Secret은 github 개인 토큰 등록
github token
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

jenkins slack 연동

설치되면 왼쪽 탐색 모음에서 Jenkins 관리를 다시 클릭한 후 시스템 구성으로 이동합니다. 글로벌 Slack Notifier 설정 섹션을 찾아 다음 값을 추가합니다.
팀 하위 도메인: 1millionteam  ( 
통합 토큰 자격 증명 ID: fufCtD3BP6qHbfFttdIQFZb3 토큰을 값으로 사용하여 암호 텍스트 자격 증명을 생성합니다.





mysql 설치
mysql install
sudo su
yum install mysql
mysql -V
mysql -h database-1.csekmcxp0nqq.ap-northeast-2.rds.amazonaws.com -u admin -p application 
-p는 데이터베이스 이름.

서비스 올리고 변경해야할 파일



--warning-mode=all --stacktrace

skiptest
./gradlew build --exclude-task test


