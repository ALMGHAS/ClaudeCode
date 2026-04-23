#!/bin/bash

# Movie Service Startup Script
# This script handles the complete setup and startup of the movie service

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if we're in the right directory
if [[ ! -f "pom.xml" ]]; then
    print_error "Please run this script from the movie-service directory"
    exit 1
fi

print_status "Starting Movie Service Setup and Launch..."
echo

# Step 1: Setup database
print_status "Step 1: Setting up database..."
if [[ -f "scripts/setup-database.sh" ]]; then
    ./scripts/setup-database.sh
else
    print_error "Database setup script not found!"
    exit 1
fi

echo
print_status "Step 2: Compiling application..."

# Clean and compile
if mvn clean compile; then
    print_success "Application compiled successfully"
else
    print_error "Compilation failed"
    exit 1
fi

echo
print_status "Step 3: Running tests..."

# Run tests (optional, can be skipped with -s flag)
if [[ "$1" != "-s" && "$1" != "--skip-tests" ]]; then
    if mvn test; then
        print_success "All tests passed"
    else
        print_warning "Some tests failed, but continuing..."
    fi
else
    print_warning "Skipping tests as requested"
fi

echo
print_status "Step 4: Starting application..."

print_success "Movie Service is starting up..."
print_status "The application will be available at:"
echo "  • Application: http://localhost:8081"
echo "  • Health Check: http://localhost:8081/actuator/health"
echo "  • API Documentation: http://localhost:8081/swagger-ui.html"
echo "  • Metrics: http://localhost:8081/actuator/metrics"
echo
print_status "Starting Spring Boot application..."

# Start the application
mvn spring-boot:run