FROM openjdk:11-jre-slim
ENV APP_HOME=/apps

ARG JAR_FILE_PATH=build/libs/application-0.0.1-SNAPSHOT.jar

WORKDIR $APP_HOME

COPY $JAR_FILE_PATH app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
# ENV	USE_PROFILE default
# ENV NORM_PROP=
# ENV DIRECT_MSG=
# COPY build/libs/*.jar dockerservice.jar
# ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-Dnormal.prop=${NORM_PROP}", "-Dconfig.healthmsg=${DIRECT_MSG}", "-jar","/dockerservice.jar"]