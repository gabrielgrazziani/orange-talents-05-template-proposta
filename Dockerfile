FROM openjdk:11
WORKDIR /usr/app/
COPY ./target/proposta.jar /usr/app/proposta.jar
EXPOSE 8080 
ENTRYPOINT java -jar proposta.jar