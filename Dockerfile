
FROM openjdk:21-jdk-slim AS build

RUN apt-get update && apt-get install -y maven

WORKDIR /library-system

COPY . .


RUN mvn clean package -DskipTests


FROM openjdk:21-jdk-slim AS runtime

WORKDIR /library-system


COPY --from=build /library-system/target/library-system-0.0.1-SNAPSHOT.jar library-system.jar


CMD ["java", "-jar", "library-system.jar"]


EXPOSE 8080
