FROM maven:3.6.3-jdk-11 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
WORKDIR /usr/app
COPY --from=builder /usr/src/app/target/proposta.jar /usr/app/proposta.jar
COPY ./target/proposta.jar /programa
EXPOSE 8080 
ENTRYPOINT java -jar proposta.jar