# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot microservices architecture project with the following services:
- **api-gateway**: Spring Cloud Gateway for routing and authentication
- **user-service**: User management with JWT authentication, JPA/MySQL, and Redis 
- **log-service**: Centralized logging with MongoDB and Kafka
- **board-service**: Board/posting service (in development)
- **infrastructure**: Docker Compose setup with Consul, databases, and supporting services

## Architecture Patterns

### Hexagonal Architecture
All services follow hexagonal (ports and adapters) architecture:
- `adapter/in/web`: REST controllers and DTOs
- `adapter/out/persistence`: Repository implementations
- `adapter/out/external`: External service integrations
- `application`: Use cases and business logic
- `domain`: Core domain models and repositories
- `shared`: Common utilities and cross-cutting concerns

### Service Communication
- **Service Discovery**: Consul for service registration and discovery
- **Inter-service Communication**: WebClient for async HTTP calls
- **Configuration**: Consul KV store for centralized configuration
- **Caching**: Redis for session management and caching
- **Logging**: Centralized logging via log-service

## Development Commands

### Building Services
```bash
# Build individual service
cd user-service && ./gradlew build
cd api-gateway && ./gradlew build
cd log-service && ./gradlew build
cd board-service && ./gradlew build

# Run tests for individual service
cd user-service && ./gradlew test
```

### Running Infrastructure
```bash
# Start all infrastructure services (databases, consul, etc.)
cd infrastructure && docker-compose up -d

# Stop infrastructure
cd infrastructure && docker-compose down

# View logs
cd infrastructure && docker-compose logs -f
```

### Running Services Locally
Services can run locally using the `local` profile, which connects to dockerized infrastructure:

```bash
# Run with local profile (connects to docker infrastructure)
cd user-service && ./gradlew bootRun --args='--spring.profiles.active=local'
cd api-gateway && ./gradlew bootRun --args='--spring.profiles.active=local'
cd log-service && ./gradlew bootRun --args='--spring.profiles.active=local'
```

### Full Docker Deployment
```bash
# Build and run all services with infrastructure
cd infrastructure && docker-compose up --build
```

## Key Technologies

- **Framework**: Spring Boot 3.4+, Spring Cloud 2024.0+
- **Build Tool**: Gradle with Java 17
- **Databases**: MySQL (user-service), MongoDB (log-service), Redis (caching)
- **Service Mesh**: Consul for service discovery and configuration
- **Security**: JWT tokens, Spring Security
- **API Documentation**: Swagger/OpenAPI 3
- **Monitoring**: Spring Actuator endpoints

## Environment Configuration

Services support multiple profiles:
- `local`: For local development against dockerized infrastructure
- `dev`: For development environment deployment

Required environment variables for local development:
- `MYSQL_ROOT_PASSWORD`, `MYSQL_USER`, `MYSQL_PASSWORD`
- `REDIS_PASSWORD`
- `MONGO_INITDB_ROOT_USERNAME`, `MONGO_INITDB_ROOT_PASSWORD`
- `ENCRYPT_KEY` (for user-service encryption)

## Service Ports

- API Gateway: 8080
- User Service: 8081  
- Log Service: 8089
- Consul: 8500
- MySQL: 3306
- Redis: 6379
- MongoDB: 27017

## Current Development Status

This codebase is actively being developed with recent work on WebClient integration in the user-service (branch: fix/user-service/webclient).