# Microservices Project: Order & Notification with Kafka and Docker

This project demonstrates a microservices architecture using two Spring Boot services communicating through Apache Kafka:

1. **Order Service** – Creates orders and publishes messages to Kafka
2. **Notification Service** – Consumes messages from Kafka and processes notifications (e.g., email notifications)

Kafka runs in **KRaft mode** (without Zookeeper) using Docker.

---

## 🚀 Quick Start

### Prerequisites

- Docker >= 24.0
- Docker Compose >= 2.0
- Java 21
- Maven 3.8+

### Getting Started

1. **Build the services:**
```bash
# Build Order Service
cd Order-Service
mvn clean package -DskipTests

# Build Notification Service  
cd ../Notification-Service
mvn clean package -DskipTests
cd ..
```

2. **Start the infrastructure:**
```bash
docker-compose up --build
```

3. **Verify services are running:**
   - Order Service: http://localhost:8080
   - Notification Service: http://localhost:8081
   - Kafka broker available on port 9094

---

## 📁 Project Structure

```
Kafka-kubernetes-Tutorial/
├── docker-compose.yaml
├── Order-Service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── Notification-Service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── README.md
```

---

## 🧪 Testing the Application

### Create an Order
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"id":1,"product":"Example Product"}'
```

### Monitor Notifications
```bash
# View all container logs
docker-compose logs -f

# Or monitor specific service
docker-compose logs -f notification-service
```

---

## 🔧 Configuration Details

### Port Mappings
- **8080:8080** → Order Service
- **8081:8080** → Notification Service  
- **9094:9094** → Kafka (external access)

### Internal Communication
Services communicate with Kafka using the internal Docker network:
```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

### Rebuilding Images
When you make code changes, rebuild the JAR files and Docker images:
```bash
# Rebuild specific service
mvn clean package -DskipTests
docker-compose build --no-cache order-service

# Or rebuild everything
docker-compose build --no-cache
```

---

## 🛠️ Development

### Monitoring Logs
```bash
# Follow logs for all services
docker-compose logs -f

# Follow logs for specific service
docker-compose logs -f order-service
docker-compose logs -f notification-service
docker-compose logs -f kafka
```

### Useful Commands
```bash
# Check running containers
docker-compose ps

# Execute commands in running containers
docker-compose exec order-service bash
docker-compose exec kafka bash

# View Kafka topics (from inside Kafka container)
kafka-topics.sh --bootstrap-server localhost:9092 --list
```

---

## 🧹 Cleanup

### Stop Services
```bash
docker-compose down
```

### Complete Cleanup (removes volumes and networks)
```bash
docker-compose down -v
docker system prune -f
```

---

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Order Service │───▶│  Kafka Broker   │───▶│Notification Svc │
│   (Port 8080)   │    │   (Port 9094)   │    │   (Port 8081)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

1. Order Service receives HTTP requests and publishes order events to Kafka
2. Kafka acts as the message broker
3. Notification Service consumes order events and processes notifications

---

## 🔍 Troubleshooting

### Common Issues

**Services not starting:**
- Ensure Docker daemon is running
- Check port availability (8080, 8081, 9094)
- Verify Java 21 is installed

**Kafka connection issues:**
- Wait for Kafka to fully start (can take 30-60 seconds)
- Check logs: `docker-compose logs kafka`

**Build failures:**
- Clean Maven cache: `mvn clean`
- Ensure correct Java version: `java -version`

### Health Checks
```bash
# Check if Order Service is healthy
curl http://localhost:8080/actuator/health

# Check if Notification Service is healthy  
curl http://localhost:8081/actuator/health
```
