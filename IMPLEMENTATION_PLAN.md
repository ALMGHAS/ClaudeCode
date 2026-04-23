# Movie Service - Implementation Plan & Checklist

## Overview
This document outlines the complete implementation plan for the Movie Service as defined in the BookMySeat PRD. The service will manage movies and their showtimes using Spring Boot 3.5.6, Java 17+, and MySQL 8.0+.

## Implementation Plan

### Phase 1: Project Setup & Configuration
1. Initialize Spring Boot project with Maven
2. Create pom.xml with all required dependencies
3. Set up application.yml configuration
4. Create project directory structure

### Phase 2: Database Layer
1. Create Movie entity class
2. Create Showtime entity class
3. Implement MovieRepository interface
4. Implement ShowtimeRepository interface
5. Create database initialization scripts (schema.sql, data.sql)

### Phase 3: Service Layer
1. Create MovieService interface and implementation
2. Create ShowtimeService interface and implementation
3. Implement business logic for filtering and data retrieval

### Phase 4: DTO Layer
1. Create MovieDTO for API responses
2. Create ShowtimeDTO for API responses
3. Create MovieDetailDTO for detailed movie info with showtimes
4. Implement DTO mappers

### Phase 5: Controller Layer
1. Create MovieController with REST endpoints
2. Implement GET /api/v1/movies endpoint
3. Implement GET /api/v1/movies/{movieId} endpoint
4. Implement GET /api/v1/showtimes endpoint
5. Add validation and error handling

### Phase 6: Exception Handling & Validation
1. Create GlobalExceptionHandler
2. Create custom exceptions (MovieNotFoundException)
3. Add Bean validation annotations
4. Implement error response DTOs

### Phase 7: Observability
1. Configure Actuator endpoints
2. Set up Prometheus metrics
3. Configure distributed tracing with Micrometer
4. Add health checks and custom metrics

### Phase 8: Documentation
1. Configure OpenAPI/Swagger
2. Add API documentation annotations
3. Configure Swagger UI

### Phase 9: Testing
1. Create unit tests for repositories
2. Create unit tests for services
3. Create integration tests for controllers
4. Add test fixtures and test data
5. Configure H2 for testing

### Phase 10: Containerization
1. Create Dockerfile
2. Create docker-compose.yml for local development
3. Add Kubernetes deployment YAML

## Verification Checklist

### ✅ Project Structure & Configuration
- [x] Maven project initialized with Spring Boot 3.5.6
- [x] Java 17+ configured
- [x] pom.xml contains all dependencies from PRD
- [x] application.yml configured with correct ports and datasource
- [x] Project runs on port 8081

### ✅ Database Schema
- [x] Movies table created with all required columns
- [x] Showtimes table created with all required columns
- [x] Foreign key constraints properly set
- [x] Indexes created for performance optimization
- [x] MySQL 8.0+ database configured

### ✅ API Endpoints
- [x] GET /api/v1/movies endpoint works
- [x] Genre filtering on movies endpoint works
- [x] Language filtering on movies endpoint works
- [x] GET /api/v1/movies/{movieId} endpoint works
- [x] 404 error returned for non-existent movie
- [x] Movie details include showtimes
- [x] GET /api/v1/showtimes endpoint works
- [x] Showtime filtering by movieId works
- [x] Showtime filtering by date works
- [x] Showtime filtering by theater works

### ✅ Data Models
- [x] Movie entity with all required fields
- [x] Showtime entity with all required fields
- [x] DTOs for request/response separation
- [x] Proper JSON serialization

### ✅ Service Layer
- [x] MovieService implemented
- [x] ShowtimeService implemented
- [x] Business logic for filtering
- [x] Proper transaction management

### ✅ Exception Handling
- [x] Global exception handler configured
- [x] Custom exceptions created
- [x] Proper HTTP status codes returned
- [x] Structured error responses

### ✅ Validation
- [x] Bean validation on request DTOs
- [x] Input validation for all endpoints
- [x] Error messages for invalid inputs

### ✅ Observability & Monitoring
- [x] /actuator/health endpoint accessible
- [x] /actuator/metrics endpoint accessible
- [x] /actuator/prometheus endpoint accessible
- [x] Health check includes database connectivity
- [x] Distributed tracing configured
- [x] Trace IDs propagated in requests

### ✅ Documentation
- [x] OpenAPI documentation at /api-docs
- [x] Swagger UI accessible at /swagger-ui.html
- [x] All endpoints documented
- [x] Request/response examples included

### ✅ Testing
- [x] Unit tests for repositories
- [x] Unit tests for services
- [x] Unit tests for controllers
- [x] Integration tests for API endpoints
- [x] Test coverage >= 85%
- [x] H2 database configured for tests

### ✅ Docker & Deployment
- [x] Dockerfile created and builds successfully
- [x] Docker image runs properly
- [x] docker-compose.yml for local development
- [x] MySQL container configured in docker-compose
- [x] Kubernetes deployment YAML created
- [x] Liveness probe configured
- [x] Readiness probe configured

### ✅ Best Practices
- [x] RESTful API design followed
- [x] Layered architecture (Controller → Service → Repository)
- [x] DTO pattern implemented
- [x] Connection pooling with HikariCP
- [x] API versioning (/api/v1/) implemented
- [x] Structured logging with correlation IDs
- [x] Proper HTTP methods and status codes used

## API Endpoints to Implement

### 1. GET /api/v1/movies
- **Description:** Retrieve all movies with optional filtering
- **Query Parameters:**
  - `genre` (optional): Filter by genre
  - `language` (optional): Filter by language
- **Response:** JSON array of movies

### 2. GET /api/v1/movies/{movieId}
- **Description:** Retrieve a specific movie with its showtimes
- **Path Parameters:**
  - `movieId`: Movie identifier
- **Response:** Movie details with showtimes
- **Error Response:** 404 Not Found if movie doesn't exist

### 3. GET /api/v1/showtimes
- **Description:** Retrieve all showtimes with optional filtering
- **Query Parameters:**
  - `movieId` (optional): Filter by movie
  - `date` (optional): Filter by date (format: YYYY-MM-DD)
  - `theater` (optional): Filter by theater name
- **Response:** JSON array of showtimes

## Database Schema

### Movies Table
```sql
CREATE TABLE movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration_minutes INT NOT NULL,
    genre VARCHAR(100),
    language VARCHAR(50),
    release_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_genre (genre),
    INDEX idx_language (language)
);
```

### Showtimes Table
```sql
CREATE TABLE showtimes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    show_date_time DATETIME NOT NULL,
    theater VARCHAR(100) NOT NULL,
    available_seats INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
    INDEX idx_movie_id (movie_id),
    INDEX idx_show_date_time (show_date_time),
    INDEX idx_theater (theater)
);
```

## Key Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Actuator
- Spring Boot Starter Validation
- MySQL Connector
- Micrometer Tracing Bridge OTel
- OpenTelemetry Exporter Zipkin
- Micrometer Registry Prometheus
- SpringDoc OpenAPI Starter WebMVC UI
- Spring Boot Starter Test
- H2 Database (for testing)

## Configuration Requirements
- Server port: 8081
- Database: MySQL 8.0+
- Health checks enabled
- Metrics exposed via Prometheus
- Distributed tracing with Zipkin
- API documentation with Swagger

## Next Steps
1. Start with Phase 1: Project setup and Maven configuration
2. Proceed through each phase systematically
3. Use this checklist to verify each requirement is met
4. Run tests after each major component is implemented
5. Verify API endpoints work correctly with proper error handling