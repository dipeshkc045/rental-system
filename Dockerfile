
FROM openjdk:21-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

WORKDIR /library-system

# Copy the entire project
COPY . .

# Build the project (skip tests)
RUN mvn clean package -DskipTests

# Runtime image
FROM openjdk:21-jdk-slim AS runtime

WORKDIR /library-system

# Copy the jar file from build to runtime
COPY --from=build /library-system/target/library-system-0.0.1-SNAPSHOT.jar library-system.jar

# Command to run the application
CMD ["java", "-jar", "library-system.jar"]

# Expose the port that your app will be listening on (usually 8080 for Spring Boot)
EXPOSE 8080
