# Use a base image with Java 21 and Maven
FROM maven:3.9.5-amazoncorretto-21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project files (pom.xml and src)
COPY pom.xml /app/pom.xml
COPY src /app/src

# Build the Spring Boot application as a WAR file
RUN mvn clean package -DskipTests -Dpackaging=war

# Use a Tomcat base image to deploy the WAR file
FROM tomcat:10.1-jdk21-openjdk-slim

# Copy the WAR file from the builder stage to the Tomcat webapps directory
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port that Tomcat listens on
EXPOSE 8080

# (Optional) Add any additional Tomcat configuration here

# No need for ENTRYPOINT, Tomcat's default will run

#docker build -t add-api1 .
#docker tag add-api1 ashrafij007/add-api1:latest
#docker push ashrafij007/add-api1:latest
