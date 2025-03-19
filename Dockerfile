FROM openjdk:23 as build
COPY mvnw .
COPY pom.xml .
COPY src src
RUN ./mvnw -DskipTests package
FROM openjdk:23
LABEL authors="Vlad"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]