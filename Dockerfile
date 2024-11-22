# Step 1: Build the application using the official Maven image
FROM maven:3.8.5-eclipse-temurin-17 AS build

# Metadata
LABEL maintainer="abhinavaggarwal23@yahoo.com"
LABEL description="A Docker image for a Jakarta EE project with Spring Data JPA and Spring MVC"

# Set the working directory
WORKDIR /app

# Copy project files to the container
COPY pom.xml .
COPY src ./src

# Run Maven to build the project
RUN mvn clean install -DskipTests

# Step 2: Create a runtime image using a lightweight JDK image
FROM eclipse-temurin:17-jdk-alpine

# Metadata
LABEL maintainer="abhinavaggarwal23@yahoo.com"
LABEL description="A Docker image for a Jakarta EE project with Spring Data JPA and Spring MVC"

# Create a volume to store temporary files
VOLUME /tmp

# Expose the port your application runs on
EXPOSE 8080

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# The entry point script starts the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
