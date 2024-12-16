# Use the openjdk image
FROM openjdk:21-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /library-system

COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Runtime image
FROM openjdk:21-jdk-slim AS runtime

WORKDIR /library-system

COPY --from=build /target/library-system-0.0.1.jar  library-system.jar

CMD ["java", "-jar", "library-system.jar"]
