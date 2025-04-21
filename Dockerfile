# Usa una imagen base de Java
FROM eclipse-temurin:21-jdk-alpine

# Establece el archivo JAR como un argumento
ARG JAR_FILE=target/andina-0.0.1-SNAPSHOT.jar

# Copia el archivo JAR al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto donde se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]