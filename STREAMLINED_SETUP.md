# 🚀 Streamlined Movie Service Setup

All manual steps have been automated! No more manual database setup, data insertion, or configuration.

## ✅ What's Now Automated

### 1. **Database Setup**
- ✅ Automatic MySQL detection (Docker or local)
- ✅ Database and user creation
- ✅ Connection testing
- ✅ Flyway migrations with sample data
- ✅ Proper indexing and constraints

### 2. **Application Configuration**
- ✅ Multi-profile support (dev/prod)
- ✅ Environment variable configuration
- ✅ Automatic timestamp handling
- ✅ Connection pooling optimization
- ✅ Health check configuration

### 3. **Data Population**
- ✅ 8 sample movies automatically inserted
- ✅ 24 sample showtimes automatically created
- ✅ Proper foreign key relationships
- ✅ No manual SQL execution needed

## 🎯 **One Command Setup**

```bash
./scripts/start.sh
```

This single command:
1. Detects your MySQL setup (Docker/local)
2. Creates database and user if needed
3. Runs all migrations and inserts sample data
4. Compiles and tests the application
5. Starts the service with full functionality

## 📋 **Setup Options**

### Option 1: Full Automated Setup
```bash
./scripts/start.sh                    # Complete setup and start
./scripts/start.sh --skip-tests       # Skip tests for faster startup
```

### Option 2: Database Only
```bash
./scripts/setup-database.sh           # Just setup database
mvn spring-boot:run                   # Start manually
```

### Option 3: Docker Compose
```bash
docker-compose up --build             # Everything in containers
```

### Option 4: Manual with Environment Variables
```bash
# Copy and customize environment
cp .env.example .env

# Start with custom config
DB_URL=jdbc:mysql://localhost:3306/my_db mvn spring-boot:run
```

## 🔧 **Configuration Profiles**

| Profile | Use Case | Features |
|---------|----------|----------|
| `dev` (default) | Development | Debug logging, test data, flexible config |
| `prod` | Production | Optimized performance, security, minimal logging |

## 🌐 **Database Compatibility**

Works with:
- ✅ **Docker MySQL** (auto-detected)
- ✅ **Local MySQL** (homebrew, manual install)
- ✅ **MySQL 8.0+** (recommended)
- ✅ **MySQL 5.7+** (supported)

## 🏥 **Health & Monitoring**

All endpoints automatically configured:
- **Health**: http://localhost:8081/actuator/health
- **Metrics**: http://localhost:8081/actuator/metrics
- **Prometheus**: http://localhost:8081/actuator/prometheus
- **API Docs**: http://localhost:8081/swagger-ui.html

## 📊 **Sample Data Included**

### Movies (8 total)
- Inception, The Dark Knight, Interstellar
- The Godfather, Pulp Fiction
- Dangal, 3 Idiots, Avengers: Endgame

### Showtimes (24 total)
- Multiple showtimes per movie
- Different theaters and times
- Available seat counts

## 🛠️ **Troubleshooting**

### Database Connection Issues
```bash
# Check if MySQL is running
./scripts/setup-database.sh

# Test connection manually
mysql -h localhost -P 3306 -u movieuser -pmoviepass movie_db
```

### Application Issues
```bash
# Clean rebuild
mvn clean install

# Check logs
mvn spring-boot:run

# Force database reset (dev only)
SPRING_PROFILES_ACTIVE=dev mvn flyway:clean flyway:migrate
```

### Docker Issues
```bash
# Reset everything
docker-compose down -v
docker-compose up --build
```

## 🎉 **Benefits of Streamlined Setup**

1. **Zero Manual Steps**: No more manual database commands
2. **Environment Flexibility**: Works with Docker or local MySQL
3. **Consistent Data**: Same sample data every time
4. **Profile Support**: Easy dev/prod configuration
5. **Error Handling**: Clear error messages and solutions
6. **Documentation**: All setup options documented

## 🔄 **Migration from Manual Setup**

If you previously set up manually:

```bash
# Clean up old setup (optional)
docker stop movie-mysql && docker rm movie-mysql

# Use new streamlined setup
./scripts/start.sh
```

Your application will now:
- Have consistent sample data
- Use proper database migrations
- Support environment-based configuration
- Include automatic health checks
- Work across different environments

## 📝 **Next Steps**

After setup completes:
1. Test APIs at http://localhost:8081/swagger-ui.html
2. Check health at http://localhost:8081/actuator/health
3. View metrics at http://localhost:8081/actuator/prometheus
4. Customize configuration in `.env` file as needed