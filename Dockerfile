FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/point-service-1.0.jar PointService.jar
ENV SPRING_PROFILES_ACTIVE prod
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/PointService.jar"]
