
FROM maven:3.8.5-openjdk-17 AS build


COPY ..

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

COPY --from=build /library-system/target/*.jar library-system.jar


ENTRYPOINT ["java", "-jar", "library-system.jar"]


EXPOSE 8080
