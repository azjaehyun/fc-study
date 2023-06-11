## spring boot sample
```
1. spring build 방법
   - ./gradlew build
   - 빌드 성공하면 ./build/libs/projectsample-0.0.1-SNAPSHOT-plain.jar 파일 생성
2. spring profile 설정법 ( project-sample-server/src/main/resources )
   - default ( application.yaml ) // h2 db 접속
   - prd ( application-prd.yaml ) // mysql db 접속
3. build된 spring boot jar 구동방법
   - -Dspring.profiles.active option 사용방법은 아래와 같습니다.
   - java -jar -Dspring.profiles.active=prd projectsample-0.0.1-SNAPSHOT.jar // prd ( application-prd.yaml으로 구동 )
   - java -jar -Dspring.profiles.active=default projectsample-0.0.1-SNAPSHOT.jar // default ( application.yaml으로 구동 )
4. docker build 
   - build path : fc-study/chapter-2/project-sample-server
   - excute command : docker build -f docker/DockerfileSpringBoot -t project2:latest .   
5. docker run -e option 리스트    
   - PROFILE
   - RDS_URL
   - RDS_USERNAME
   - RDS_USERPW
6. docker run -e option 사용법
   - env option 사용법 : docker run -p 80:8888 -e PROFILE=prd -e RDS_URL=[your_rds_url] -e RDS_USERNAME=[your_rds_username] -e RDS_USERPW=[your_rds_pw] dockerimages이름
   - ex) docker run -p 8888:8888 -e PROFILE=default project2:latest
```


## spring profile prd 사전준비
```
cd docker
cat REAMDE.md 참고.
```

