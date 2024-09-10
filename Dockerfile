FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/moneytransfer-0.0.1-SNAPSHOT.jar moneytransfer.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","demo.jar"]
