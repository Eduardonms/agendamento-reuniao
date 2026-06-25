FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY src ./src

RUN chmod +x mvnw && ./mvnw -B package -DskipTests -Dquarkus.profile=prod

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/quarkus-app/ /app/

EXPOSE 8080
CMD ["java", "-jar", "quarkus-run.jar"]
