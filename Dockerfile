# Use a base image with Java and Maven installed
FROM maven:3.8.4-openjdk-17-slim AS build
# Set the working directory inside the container
WORKDIR /app
# Copy the pom.xml file
COPY pom.xml .
# Download dependencies and cache them in a separate layer
RUN mvn dependency:go-offline -B
# Copy the source code into the container
COPY src ./src
# Build the JAR file
RUN mvn package -DskipTests
# Start a new stage to create a lightweight image
FROM openjdk:17-slim
# Set the working directory inside the container
WORKDIR /app
# Copy the JAR file from the previous stage
COPY --from=build /app/target/papss-incoming-request-service-*.jar app.jar
# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]