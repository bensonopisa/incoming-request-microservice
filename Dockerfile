FROM openjdk:17-alpine

LABEL MAINTAINER = "bopisa@ipsl.co.ke"

ENV TZ=Africa/Nairobi

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apk --no-cache add curl

# cd /app
WORKDIR /app

# Refer to Maven build -> finalName
ARG JAR_FILE=target/papss-incoming-request-service-*.jar

ARG KEY_STORE_FILE_PATH=target/classes/client.jks

COPY ${KEY_STORE_FILE_PATH} client.jks

# cp target/spring-gitlab-ci-0.0.1-SNAPSHOT.jar /app/spring-gitlab-ci.jar
COPY ${JAR_FILE} app.jar

# java -jar /app/spring-gitlab-ci.jar
# CMD ["java", "-jar", "-Xmx1024M",  "-Dspring.profiles.active=dev", "/app/confirmation-service.jar"]
CMD ["java", "-jar", "-Xmx1024M","-Dpapss.security.keyStorePath=/app/client.jks","/app/app.jar"]
