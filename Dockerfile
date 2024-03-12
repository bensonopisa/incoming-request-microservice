# Use a base image with Java 17 (You can choose a different base image if needed)
# creates a layer from the openjdk:17-alpine Docker image.
FROM openjdk:17-alpine

LABEL maintainer="idiba@user.co.ke"

# Install curl
RUN apk --no-cache add curl

# cd /app
WORKDIR /app

# Refer to Maven build -> finalName
ARG JAR_FILE=target/cbk-gdi-*.jar

# cp target/spring-gitlab-ci-0.0.1-SNAPSHOT.jar /app/spring-gitlab-ci.jar
COPY ${JAR_FILE} cbk-gdi.jar

# java -jar /app/spring-gitlab-ci.jar
CMD ["java", "-jar", "-Xmx1024M",  "-Dspring.profiles.active=dev", "/app/cbk-gdi.jar"]

EXPOSE 8700