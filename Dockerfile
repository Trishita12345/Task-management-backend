# Step 1: Use a base image with Java
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy the jar file into the container
COPY target/task-management-be-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Tell Docker how to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
