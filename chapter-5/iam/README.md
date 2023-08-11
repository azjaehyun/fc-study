### 1. ecr-upload.json 파일을 사용하여 ecr-upload 이름으로 policy 생성.

### 2. 아래 1개의 권한을 넣고 ecr-upload-role 이름으로 역할 생성 - jenkins pipeline withAWS 사용시 사용
- 보안자격증명 > 역활 탭 이동 > 역할 만들기 > Aws 계정으로 생성 
```
ecr-upload // ecr-upload.json 
```