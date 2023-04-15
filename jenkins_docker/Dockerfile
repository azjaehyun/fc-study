FROM jenkins/jenkins:2.387.2
#FROM jenkins/jenkins:latest

USER root

RUN apt-get update && \
    apt-get -y install apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
   apt-get update && \
   apt-get -y install docker-ce
   
RUN groupadd -f docker
RUN usermod -aG docker jenkins

LABEL maintainer="azjaehyun@gmail.com"
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=true
RUN jenkins-plugin-cli \
    --plugins \
    git \
    workflow-aggregator \
    blueocean \
    docker-plugin \
    ant \
    gradle \
    pipeline-aws \
    pipeline-build-step \
    pipeline-github-lib \
    pipeline-rest-api \
    pipeline-stage-view \
    timestamper \
    aws-credentials \
    file-operations \
    branch-api \
    workflow-support \
    pam-auth \
    ldap \
    email-ext \
    matrix-auth \
    ws-cleanup \
    resource-disposer \
    antisamy-markup-formatter \
    build-timeout 
# docker pull symjaehyun/jenkins:latest 