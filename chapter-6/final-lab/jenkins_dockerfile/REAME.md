# jenkins plugin all-in-one ( docker in docker )
  
## docker image pull
```
docker pull symjaehyun/jenkins:latest  // plugin 설치 버전.
```

## docker in docker run
```
docker run -d -v /var/run/docker.sock:/var/run/docker.sock -p 80:8080 symjaehyun/jenkins:latest
```