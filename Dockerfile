# ====== Stage 1: Build the JAR ======
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application (creates jar inside target/)
RUN mvn clean install -DskipTests

# ====== Stage 2: Run the JAR ======
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/task-management-be-0.0.1-SNAPSHOT.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
