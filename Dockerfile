FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
WORKDIR /MoneyTransfer
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar MoneyTransfer.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","MoneyTransfer.jar"]
