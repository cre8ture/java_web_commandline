# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.2-openjdk-8-slim AS build

# Copy local code to the container image.
WORKDIR /app
COPY . .

# Build a release artifact.
RUN mvn package -DskipTests

# Use OpenJDK for runtime
FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/*.jar

# cd /opt/$APP_DIR
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/$APP_DIR/app.jar
COPY --from=build ${JAR_FILE} app.jar

# java -jar /opt/$APP_DIR/app.jar
ENTRYPOINT ["java","-jar","app.jar"]

# Expose the port
EXPOSE 8080