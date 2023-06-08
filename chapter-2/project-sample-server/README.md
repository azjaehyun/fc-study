## spring boot sample
```
1. spring profile 설정법 ( project-sample-server/src/main/resources )
   - default ( application.yaml ) // h2 db 접속
   - prd ( application-prd.yaml ) // mysql db 접속
2. spring boot jar 구동방법
   - -Dspring.profiles.active option 사용방법은 아래와 같습니다.
   - java -jar -Dspring.profiles.active=prd projectsample-0.0.1-SNAPSHOT.jar // prd ( application-prd.yaml으로 구동 )
   - java -jar -Dspring.profiles.active=default projectsample-0.0.1-SNAPSHOT.jar // default ( application.yaml으로 구동 )

```


## spring profile prd 사전준비
```
cd docker
cat REAMDE.md 참고.
```

