```dockerfile
FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/point-service-2-0.0.1-SNAPSHOT.jar PointService.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/PointService.jar"]
```

- `FROM openjdk:17-ea-11-jdk-slim`: This is the base image for the Docker image. It is a slim version of the JDK 11 image. The slim version is used to reduce the size of the image. The image is based on Debian Linux.
- `VOLUME /tmp`: This is a Docker volume. It is used to store temporary files. The volume is mounted to the `/tmp` directory in the container.
- `COPY target/point-service-2-0.0.1-SNAPSHOT.jar PointService.jar`: This command copies the JAR file from the `target` directory to the Docker image. The JAR file is named `PointService.jar`.
- `ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/PointService.jar"]`: This command is used to run the JAR file. The `ENTRYPOINT` command is used to run the JAR file when the container is started. The `ENTRYPOINT` command is similar to the `CMD` command. The difference is that the `ENTRYPOINT` command is not overridden when the container is started. The `ENTRYPOINT` command is used to run the JAR file when the container is started. The `ENTRYPOINT` command is similar to the `CMD` command. The difference is that the `ENTRYPOINT` command is not overridden when the container is started.
- `-Djava.security.egd=file:/dev/./urandom`: This parameter is used to improve the performance of the random number generator. It is recommended to use this parameter when using the Docker container
