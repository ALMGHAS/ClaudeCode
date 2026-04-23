#!/bin/bash

# Movie Service Database Setup Script
# This script sets up the database for both Docker and local MySQL installations

set -e

# Default values
DB_NAME="movie_db"
DB_USER="movieuser"
DB_PASSWORD="moviepass"
DB_ROOT_PASSWORD="rootpass"
DB_HOST="localhost"
DB_PORT="3306"
DOCKER_CONTAINER_NAME="movie-mysql"

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

# Function to check if Docker is running
check_docker() {
    if ! command -v docker &> /dev/null; then
        return 1
    fi
    if ! docker info &> /dev/null; then
        return 1
    fi
    return 0
}

# Function to check if MySQL is running locally
check_local_mysql() {
    if command -v mysql &> /dev/null; then
        # Check if MySQL service is running (Windows/Linux compatible)
        if netstat -an 2>/dev/null | grep -q ":3306.*LISTEN" || \
           ss -tuln 2>/dev/null | grep -q ":3306" || \
           mysql -h localhost -P 3306 -u root -e "SELECT 1;" &> /dev/null; then
            return 0
        fi
    fi
    return 1
}

# Function to setup Docker MySQL
setup_docker_mysql() {
    print_status "Setting up MySQL with Docker..."

    # Check if container already exists
    if docker ps -a --format '{{.Names}}' | grep -q "^${DOCKER_CONTAINER_NAME}$"; then
        print_warning "Container ${DOCKER_CONTAINER_NAME} already exists."
        if docker ps --format '{{.Names}}' | grep -q "^${DOCKER_CONTAINER_NAME}$"; then
            print_success "MySQL container is already running."
            return 0
        else
            print_status "Starting existing container..."
            docker start ${DOCKER_CONTAINER_NAME}
            sleep 10
            return 0
        fi
    fi

    print_status "Creating new MySQL container..."
    docker run -d --name ${DOCKER_CONTAINER_NAME} \
        -e MYSQL_DATABASE=${DB_NAME} \
        -e MYSQL_USER=${DB_USER} \
        -e MYSQL_PASSWORD=${DB_PASSWORD} \
        -e MYSQL_ROOT_PASSWORD=${DB_ROOT_PASSWORD} \
        -p ${DB_PORT}:3306 \
        mysql:8.0

    print_status "Waiting for MySQL to be ready..."
    local attempt=1
    local max_attempts=30

    while [ $attempt -le $max_attempts ]; do
        if docker exec ${DOCKER_CONTAINER_NAME} mysql -u root -p${DB_ROOT_PASSWORD} -e "SELECT 1;" &> /dev/null; then
            print_success "MySQL is ready!"
            break
        fi
        print_status "Attempt $attempt/$max_attempts - waiting for MySQL..."
        sleep 2
        attempt=$((attempt + 1))
    done

    if [ $attempt -gt $max_attempts ]; then
        print_error "MySQL failed to start within expected time."
        return 1
    fi

    print_success "Docker MySQL setup completed!"
}

# Function to setup local MySQL
setup_local_mysql() {
    print_status "Setting up local MySQL database..."

    # Try to connect as root without password first
    if mysql -h localhost -P 3306 -u root -e "SELECT 1;" &> /dev/null; then
        ROOT_CMD="mysql -h localhost -P 3306 -u root"
    else
        print_status "Please enter MySQL root password:"
        read -s ROOT_PASSWORD
        ROOT_CMD="mysql -h localhost -P 3306 -u root -p${ROOT_PASSWORD}"
    fi

    print_status "Creating database and user..."
    $ROOT_CMD << EOF
CREATE DATABASE IF NOT EXISTS ${DB_NAME};
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';
CREATE USER IF NOT EXISTS '${DB_USER}'@'%' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'localhost';
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'%';
FLUSH PRIVILEGES;
EOF

    print_success "Local MySQL setup completed!"
}

# Function to test database connection
test_connection() {
    print_status "Testing database connection..."

    if mysql -h ${DB_HOST} -P ${DB_PORT} -u ${DB_USER} -p${DB_PASSWORD} ${DB_NAME} -e "SELECT 1;" &> /dev/null; then
        print_success "Database connection successful!"
        return 0
    else
        print_error "Database connection failed!"
        return 1
    fi
}

# Main setup function
main() {
    print_status "Starting Movie Service Database Setup..."
    echo

    # Check what's available
    if check_docker; then
        print_status "Docker is available."
        if check_local_mysql; then
            print_status "Local MySQL is also available."
            echo
            echo "Choose setup option:"
            echo "1) Use Docker MySQL (recommended for development)"
            echo "2) Use Local MySQL"
            echo "3) Auto-detect and use existing setup"
            echo
            read -p "Enter choice [1-3]: " choice

            case $choice in
                1)
                    setup_docker_mysql
                    ;;
                2)
                    setup_local_mysql
                    ;;
                3)
                    if docker ps --format '{{.Names}}' | grep -q "^${DOCKER_CONTAINER_NAME}$"; then
                        print_success "Using existing Docker MySQL container."
                    elif check_local_mysql; then
                        setup_local_mysql
                    else
                        setup_docker_mysql
                    fi
                    ;;
                *)
                    print_error "Invalid choice. Using Docker MySQL."
                    setup_docker_mysql
                    ;;
            esac
        else
            setup_docker_mysql
        fi
    elif check_local_mysql; then
        print_status "Using local MySQL (Docker not available)."
        setup_local_mysql
    else
        print_error "Neither Docker nor local MySQL is available."
        print_error "Please install Docker or MySQL first."
        exit 1
    fi

    # Test the connection
    sleep 2
    if test_connection; then
        echo
        print_success "Database setup completed successfully!"
        echo
        print_status "Connection details:"
        echo "  Host: ${DB_HOST}"
        echo "  Port: ${DB_PORT}"
        echo "  Database: ${DB_NAME}"
        echo "  Username: ${DB_USER}"
        echo "  Password: ${DB_PASSWORD}"
        echo
        print_status "You can now run the application with:"
        echo "  mvn spring-boot:run"
        echo
        print_status "Or with Docker Compose:"
        echo "  docker-compose up"
    else
        print_error "Database setup failed. Please check the logs and try again."
        exit 1
    fi
}

# Run main function
main