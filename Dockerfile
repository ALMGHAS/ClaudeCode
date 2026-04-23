## Build stage: run Maven before copying the JAR
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

# Run Maven commands before producing the JAR
RUN mvn -B clean compile -DskipTests
RUN mvn -B package -DskipTests


## Runtime stage: slim JRE image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install wget for health check (Alpine doesn't include it by default)
RUN apk add --no-cache wget

# Create a non-root user
RUN addgroup -g 1001 -S movieservice && \
    adduser -S movieservice -u 1001 -G movieservice

# Copy the jar file built in the first stage
COPY --from=build /build/target/movie-service-*.jar app.jar

# Change ownership of the app
RUN chown movieservice:movieservice app.jar

# Switch to non-root user
USER movieservice

# Expose port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
