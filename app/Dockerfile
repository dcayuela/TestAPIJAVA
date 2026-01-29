# Etape 1: Construction de l'application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Etape 2: Execution de l'application
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Copie le JAR construit dans l'étape de buid
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# La commande ENTRYPOINT est gérée entièrement par docker-compose.yml
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

