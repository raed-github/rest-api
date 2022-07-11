FROM openjdk:11 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

From openjdk:11
ADD ./target/transaction-service-0.0.1-SNAPSHOT.jar transaction-service-0.0.1-SNAPSHOT.jar
EXPOSE 8092
CMD ["java","-jar","transaction-service-0.0.1-SNAPSHOT.jar"]
