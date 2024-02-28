FROM maven:3.8.4-openjdk-17 as build

COPY src /usr/src/gateway/src
COPY pom.xml /usr/src/gateway
RUN mvn -f /usr/src/gateway/pom.xml clean package

FROM openjdk:17

COPY --from=build /usr/src/gateway/target/gateway-0.0.1-SNAPSHOT.jar /usr/src/gateway/app.jar
WORKDIR /usr/src/gateway
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
