FROM openjdk:23 AS build
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN ./mvnw -DskipTests package

FROM openjdk:23
LABEL authors="Vlad"
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]