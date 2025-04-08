FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/nice-equipe-football-api-0.0.1-SNAPSHOT.jar nice-equipe-football-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "nice-equipe-football-api.jar"]
