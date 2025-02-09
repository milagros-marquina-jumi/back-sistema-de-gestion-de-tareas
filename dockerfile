# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Copia el archivo JAR de tu aplicación (asegúrate de tenerlo previamente construido)
COPY target/backend-tareas-0.0.1-SNAPSHOT.jar /app/backEnd.jar

# Establece el directorio de trabajo
WORKDIR /app

# Expón el puerto que tu aplicación escucha
EXPOSE 9090

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "backEnd.jar"]
