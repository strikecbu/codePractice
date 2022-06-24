FROM maven:3.8.6-eclipse-temurin-17-alpine as build

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY pom.xml pom.xml
RUN mvn verify --fail-never

COPY . .

RUN mvn package

FROM openjdk:17.0.2-jdk-slim

WORKDIR /app

COPY --from=build /usr/app/target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT java -jar /app/accounts-0.0.1-SNAPSHOT.jar

