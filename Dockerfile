
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /library-system

COPY . .

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim AS runtime

WORKDIR /library-system

COPY --from=build /app/target/*.jar library-system.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "library-system.jar"]
