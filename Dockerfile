FROM openjdk:23 AS build
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src
RUN chmod +x mvnw && ./mvnw -DskipTests -B package
FROM openjdk:23
LABEL authors="Vlad"
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]