# web-smart-storage

A multi-module Spring Boot application following hexagonal architecture principles.

## Project Structure

This project is organized as a Gradle multi-module project with the following modules:

### Module: `domain`
- **Purpose**: Pure business logic layer (core domain)
- **Dependencies**: Minimal - only Lombok for reducing boilerplate
- **No Spring dependencies** - keeping the domain layer framework-agnostic

### Module: `data`
- **Purpose**: JPA entities and Spring Data repositories
- **Dependencies**: Spring Data JPA, domain module
- **Provides**: Database access layer

### Module: `infra`
- **Purpose**: Infrastructure adapters (WebClient, WebFlux, external service adapters)
- **Dependencies**: WebClient, WebFlux, domain module
- **Provides**: External service integrations

### Module: `api-service`
- **Purpose**: Application layer (REST API, use cases, main application entry point)
- **This is the only bootable module**
- **Dependencies**: All other modules (domain, data, infra), Spring Boot starters
- **Entry Point**: `hu.ps.ss.apiservice.WebSmartStorageApplication`

## Building the Project

Build the entire project:
```bash
./gradlew build
```

Build a specific module:
```bash
./gradlew :domain:build
./gradlew :data:build
./gradlew :infra:build
./gradlew :api-service:build
```

## Running the Application

Run the application:
```bash
./gradlew :api-service:bootRun
```

The application will start on port 8080 with an in-memory H2 database.

## Technology Stack

- **Java**: 25
- **Gradle**: 8.14
- **Spring Boot**: 4.0.2
- **Database**: H2 (in-memory, for development)
- **Lombok**: For reducing boilerplate code

## Architecture

The project follows hexagonal architecture (ports and adapters):
- **Domain**: Core business logic, independent of any framework
- **Data**: Persistence adapters
- **Infra**: External service adapters
- **API Service**: Application layer orchestrating all modules

