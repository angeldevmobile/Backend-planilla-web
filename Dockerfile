# Etapa 1: Construcción del .jar usando Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia todo el proyecto (incluyendo pom.xml y src/)
COPY . .

# Compila el proyecto y salta los tests
RUN mvn clean package -DskipTests

# Etapa 2: Contenedor final con solo Java
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia solo el .jar generado por la etapa anterior
COPY --from=builder /app/target/SmartPayroll-0.0.1-SNAPSHOT.jar app.jar

# Puerto usado por Spring Boot (Render mapea automáticamente)
ENV PORT=8080
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
