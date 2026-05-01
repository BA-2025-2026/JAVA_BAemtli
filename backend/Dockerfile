# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy gradle executable and configuration
COPY gradlew build.gradle settings.gradle ./
COPY gradle/ gradle/

# Download dependencies (caching layer)
RUN ./gradlew build -x test --no-daemon || return 0

# Copy source code and build the jar
COPY src ./src
RUN ./gradlew clean bootJar -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Security: Run as non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the executable jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]