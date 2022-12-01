# Build
FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

# Package
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar point-service-2.jar
EXPOSE 8004
ENTRYPOINT ["java","-jar","point-service-2.jar"]
