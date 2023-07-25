## jenkins plugin all-in-one ( docker in docker )
## 해당 도커 파일을 빌드해서 올리기 싫으신분들은 아래에 제 도커허브에서 받아가시면 됩니다 :)

## docker image pull
```
docker pull symjaehyun/jenkins:latest  // plugin 설치 버전.
```

## docker in docker run
```
docker run -d -v /var/run/docker.sock:/var/run/docker.sock -p 80:8080 symjaehyun/jenkins:latest
```