FROM jenkins/jenkins:2.387.2
#FROM jenkins/jenkins:latest
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