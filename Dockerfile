
FROM eclipse-temurin:17-jdk-alpine


ARG JAR_FILE=target/andina-0.0.1-SNAPSHOT.jar


COPY target/andina-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]