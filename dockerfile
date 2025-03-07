# Use Gradle with JDK 18 for building
FROM gradle:jdk18 AS build

# Set work directory
WORKDIR /home/gradle/src

# Copy project files
COPY --chown=gradle:gradle . /home/gradle/src

# Build the Spring Boot application
RUN gradle --no-daemon bootJar

# Use OpenJDK 18 for running the app
FROM openjdk:18

# Create app directory
RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "-Xmx4g", "/app/spring-boot-application.jar"]
