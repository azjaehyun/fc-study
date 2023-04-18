## eks bastion 서버 및 환경 구축
- cloud9 환경에서 실행합니다.
---

1. Cloud9 환경에서 eks bastion 서버 및 인프라를 생성합니다. 
 - 사전준비 aws configure 설정 후 진행!! terraform.tfvars 에 적용!!
```
> cd terra-middle-class/terraform/arch/terraform-middle/dev/20-vpc-subnet-eks-bastion
> terraform init
> terraform plan
> terraform apply
```

2. Bastion 서버가 생성되면 bastion서버에 필요한필요한 library를 ansible을 이용해 설치합니다.   
따라서 cloud9 환경에서 Ansible을 사용하기 위해 cloud9 환경에 ansible을 설치합니다.
```
sudo su // root 권한으로 설치
apt-get update
apt-get install apt-transport-https wget gnupg
apt-add-repository ppa:ansible/ansible
apt-get update
apt-get install ansible
ansible --version
// version 체크
ansible 2.9.27
  config file = /etc/ansible/ansible.cfg
  configured module search path = [u'/root/.ansible/plugins/modules', u'/usr/share/ansible/plugins/modules']
  ansible python module location = /usr/lib/python2.7/dist-packages/ansible
  executable location = /usr/bin/ansible
  python version = 2.7.17 (default, Nov 28 2022, 18:51:39) [GCC 7.5.0]
```
3. Ansible을 실행시켜서 bastion 서버에 필요한 library를 설치합니다.
```
> cd terra-middle-class/terraform/arch/terraform-middle/dev/10-init-cloud9/cloud9-setting/packer/bastion-server/ansible
> vi inventory.yml // 수정 bastion ip , pem key setting - 1번에서 생성된 pem 키

```
4. asible로 bastion서버에 library 설치
~~~
### asible로 bastion서버에 library 설치 실행
> cd terra-middle-class/terraform/arch/terraform-middle/dev/10-init-cloud9/cloud9-setting/packer/bastion-server/ansible 
> ansible-playbook -i inventory.yml playbook-bastion.yml

// 실행 결과
PLAY [Provisioning] ***********************************************


생략...

TASK [postgressql-client : install postgresql-client 12] ****************************
changed: [image.builder]

PLAY RECAP *********************************************************
image.builder              : ok=70   changed=34   unreachable=0    failed=0    skipped=1    rescued=0    ignored=0   
~~~


5. Bastion 서버에 접속해서 ansible로 잘 설치 되었는지 확인합니다.  
```
> ssh -i {1번에서 생성한 pem.key}  ubuntu@{bastion_public_ip}
// example 
> ssh -i middleClass-key.pem  ubuntu@1.33.11.33

ssh로 접속후 bastion에 ansible로 library가 잘 설치되었는지 확인!
---
> java --version
openjdk 11.0.17 2022-10-18
OpenJDK Runtime Environment (build 11.0.17+8-post-Ubuntu-1ubuntu220.04)
OpenJDK 64-Bit Server VM (build 11.0.17+8-post-Ubuntu-1ubuntu220.04, mixed mode, sharing)

> aws --version
aws-cli/2.9.17 Python/3.9.11 Linux/5.15.0-1028-aws exe/x86_64.ubuntu.20 prompt/off
> helm version
version.BuildInfo{Version:"v3.11.0", GitCommit:"472c5736ab01133de504a826bd9ee12cbe4e7904", GitTreeState:"clean", GoVersion:"go1.18.10"}

> istioctl version
unable to retrieve Pods: Get "http://localhost:8080/api/v1/namespaces/istio-system/pods?fieldSelector=status.phase%3DRunning&labelSelector=app%3Distiod": dial tcp 127.0.0.1:8080: connect: connection refused
1.9.2

> terraform version
Terraform v0.14.0
Your version of Terraform is out of date! The latest version
is 1.3.7. You can update by downloading from https://www.terraform.io/downloads.html

이하 생략..

```
6. bastion 서버에서 Terraform 으로 eks를 생성합니다.
```
```


부록 terraform.tfstate 업로드 // cloud9 삭제시 필요.
~~~
>> cloud9서버에서 terraform.tfstate upload
aws s3 cp  /home/ubuntu/environment/terra-middle-class/terraform/arch/terraform-middle/dev/20-vpc-subnet-eks-bastion/terraform.tfstate s3://jaehyun-terraform-tfstate/bastion-state

>> s3에서 bastion server로 terraform.tfstate download
aws s3 cp s3://jaehyun-terraform-tfstate/bastion-state /home/ubuntu/terra-middle-class/terraform/arch/terraform-middle/dev/20-vpc-subnet-eks-bastion/terraform.tfstate
~~~