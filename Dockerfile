# Dockerfile-spring
# A estratégia usada aqui é a multi-stage build
# Foi utilizado esta estratégia pois ainda não temos o CI/CD para fazer a orquestração das fazes

# Utilizando a imagem com Maven para compilar a aplicação
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

# Diretório de trabalho no container fase de build
WORKDIR /app

# Copia dos fontes do projeto para o container
COPY src /app/src
COPY pom.xml /app

# Construção da aplicação usando Maven
RUN mvn clean install -DskipTests

# Para a fase de execução é utilizado a imagem Java baseada no JRE
FROM eclipse-temurin:21-jdk-alpine

# Copiando o arquivo jar da fase de build
COPY --from=build /app/target/grupo48-orquestrador-0.0.1-SNAPSHOT.jar /app.jar

# Porta padrão da aplicação Spring Boot
EXPOSE 8089

# Montagem de volume para registro de logs
VOLUME /logs

# Comando para executar a aplicação
CMD ["java", "-jar", "/app.jar"]
