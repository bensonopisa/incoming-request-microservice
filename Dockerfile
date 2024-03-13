FROM openjdk:17-alpine

LABEL MAINTAINER = "bopisa@ipsl.co.ke"

ENV TZ=Africa/Nairobi

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apk --no-cache add curl

# cd /app
WORKDIR /app

# Refer to Maven build -> finalName
ARG JAR_FILE=target/papss-incoming-request-service-*.jar

# copy the client keystore to the root of the keystore
COPY src/main/resources/client.jks /app/client.jks

# copy the archived file and rename it as app.jar
COPY ${JAR_FILE} app.jar

ENV JAVA_OPTS="-Djavax.net.ssl.keyStore=/app/client.jks -Djavax.net.ssl.keyStorePassword=secret"

# run the application
CMD ["java", "-jar", "-Xmx1024M","/app/app.jar"]
