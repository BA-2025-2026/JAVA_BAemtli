# Stage 1: Build
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN chmod +x gradlew && ./gradlew build --no-daemon -x test

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


