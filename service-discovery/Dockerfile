FROM openjdk:11 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

From openjdk:11
ADD ./target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery-0.0.1-SNAPSHOT.jar
EXPOSE 8761
CMD ["java","-jar","service-discovery-0.0.1-SNAPSHOT.jar"]
