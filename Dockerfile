FROM openjdk:11
WORKDIR /programa
COPY ./target/proposta.jar /programa
EXPOSE 8080 
ENTRYPOINT java -jar proposta.jar