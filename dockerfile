# Nutze das Gradle-Build-Image mit JDK 18
FROM gradle:jdk18 AS build

# Setze das Arbeitsverzeichnis
WORKDIR /home/gradle/src

# Kopiere das komplette Gradle-Projekt aus dem "Project"-Ordner
COPY Project /home/gradle/src

# Erstelle die Spring Boot JAR-Datei
RUN gradle --no-daemon bootJar

# Wechsle zum Laufzeit-Image mit OpenJDK 18
FROM openjdk:18

# Erstelle ein Verzeichnis für die App
RUN mkdir /app

# Kopiere die gebaute JAR-Datei ins Laufzeit-Image
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

# Exponiere den Port 8080
EXPOSE 8080

# Starte die Spring Boot Anwendung
CMD ["java", "-jar", "-Xmx4g", "/app/spring-boot-application.jar"]
