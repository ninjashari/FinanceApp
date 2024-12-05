# Step 1: Use JDK 23 for building the application
FROM eclipse-temurin:23-jdk-alpine AS build

# Set working directory inside the container
WORKDIR /app

# Copy Maven Wrapper and configuration
COPY mvnw ./
COPY .mvn .mvn

# Copy Maven configuration and source code
COPY pom.xml ./
COPY src ./src

# Ensure `mvnw` is executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Step 2: Use JDK 23 for running the application
FROM eclipse-temurin:23-jre-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (adjust if necessary)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
