FROM maven:3.9.6-eclipse-temurin-17 as build

WORKDIR /app
COPY pom.xml .
COPY settings.xml .

RUN mvn dependency:resolve -s settings.xml

COPY ./src src/

RUN mvn install -s settings.xml




FROM maven:3.9.6-eclipse-temurin-17

RUN apt-get update && apt-get install -y curl openssl
RUN apt-get update && apt-get install -y openssl

ENV export DOCKER_TLS_VERIFY=0

WORKDIR /app


COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080/tcp

ENV JAVA_OPTS="-Dcom.sun.net.ssl.checkRevocation=false -Djavax.net.ssl.trustStoreType=JKS"

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
