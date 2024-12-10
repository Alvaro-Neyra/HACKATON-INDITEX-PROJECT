# Etapa de construcción
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/inditex-backend-java-logisticode.jar app.jar

EXPOSE 3000

CMD ["java", "-jar", "app.jar"]
