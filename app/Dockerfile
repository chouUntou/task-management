# Use a multi-stage build

# Stage 1: Build the JAR using Gradle
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# Copy only the necessary files first to leverage Docker cache
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Build the project and generate the JAR file
#RUN gradle clean build -x test
RUN gradle clean build

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/taskmanagement-api-0.0.1.jar app.jar

EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
