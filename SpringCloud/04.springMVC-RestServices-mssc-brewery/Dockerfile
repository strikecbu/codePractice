FROM maven:3.8.5-jdk-11 as maven

WORKDIR /build

COPY . .

RUN mvn package

FROM openjdk:11-jdk-oraclelinux7

WORKDIR /app

ARG jarFile=mssc-brewery-0.0.1-SNAPSHOT.jar
ENV file $jarFile

COPY --from=maven /build/target/${file} .

EXPOSE 8080

CMD ["sh", "-c", "java -jar ${file}"]
