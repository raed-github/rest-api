FROM openjdk:11 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

From openjdk:11
ADD ./target/account-service-0.0.1-SNAPSHOT.jar account-service-0.0.1-SNAPSHOT.jar
EXPOSE 8090
CMD ["java","-jar","account-service-0.0.1-SNAPSHOT.jar"]
