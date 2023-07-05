# Use a base image with Java and Maven installed
FROM maven:3.8.3-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Download the dependencies specified in the pom.xml file
RUN mvn dependency:go-offline -B

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create a new container using a lightweight base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build container to the current directory in the container
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application listens on
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "app.jar"]

