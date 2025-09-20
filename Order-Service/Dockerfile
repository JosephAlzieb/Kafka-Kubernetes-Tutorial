#Build-Stage
#FROM maven:3.9-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests

# Basis-Image mit Java 17
FROM eclipse-temurin:21-jdk-alpine

# Arbeitsverzeichnis im Container
WORKDIR /Order-Service

# JAR-Datei ins Image kopieren
COPY target/*.jar app.jar

# Container-Start-Befehl
ENTRYPOINT ["java", "-jar", "app.jar"]
