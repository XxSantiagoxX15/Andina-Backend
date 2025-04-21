# Imagen base con Java 21
FROM eclipse-temurin:21-jdk-alpine

# Nombre del archivo JAR
ARG JAR_FILE=target/andina-0.0.1-SNAPSHOT.jar

# Copiar el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Exponer el puerto (puerto por defecto de Spring Boot)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
