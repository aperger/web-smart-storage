# GitHub Copilot Instructions for Web Smart Storage

## Project Overview

Web Smart Storage is a multi-module Spring Boot application designed following **hexagonal architecture** (ports and adapters) principles. The project emphasizes clean separation of concerns, framework independence in the core domain, and maintainability through clear module boundaries.

### Key Characteristics
- **Architecture Pattern**: Hexagonal Architecture (Ports and Adapters)
- **Project Type**: Multi-module Gradle project
- **Primary Language**: Java 25
- **Framework**: Spring Boot 4.0.2
- **Build Tool**: Gradle 8.14
- **Development Database**: H2 (in-memory)

## Module Structure and Responsibilities

The project is divided into four distinct modules, each with specific responsibilities:

### 1. `domain/` - Core Domain Layer
**Purpose**: Pure business logic and domain entities

**Key Principles**:
- **Framework-Independent**: Contains ZERO Spring dependencies
- **Business Logic Only**: Pure Java domain models, value objects, and business rules
- **Minimal Dependencies**: Only uses Lombok for reducing boilerplate
- **No Side Effects**: Domain objects should not perform I/O operations

**Package Structure**:
```
hu.ps.ss.domain/
  ├── model/        # Domain entities and value objects
  ├── service/      # Domain services (business logic)
  └── exception/    # Domain-specific exceptions
```

**What to put here**:
- Business entities and aggregates
- Value objects
- Domain services with pure business logic
- Domain events
- Business rules and validations

**What NOT to put here**:
- Spring annotations (@Component, @Service, etc.)
- Database entities (@Entity)
- HTTP/REST concerns
- Infrastructure dependencies

### 2. `data/` - Persistence Adapter Layer
**Purpose**: Database access and JPA entities

**Key Principles**:
- Implements persistence ports defined in domain
- Contains JPA entities that map to database tables
- Uses Spring Data JPA repositories
- Translates between domain models and JPA entities

**Dependencies**: `domain`, `spring-boot-starter-data-jpa`

**Package Structure**:
```
hu.ps.ss.data/
  ├── entity/       # JPA entities (@Entity)
  ├── repository/   # Spring Data JPA repositories
  └── mapper/       # Mappers between domain and JPA entities
```

**What to put here**:
- JPA entities with @Entity, @Table, etc.
- Spring Data repositories (interfaces extending JpaRepository)
- Database-specific configurations
- Entity mappers (domain ↔ JPA entity)

### 3. `infra/` - Infrastructure Adapter Layer
**Purpose**: External service integrations and technical infrastructure

**Key Principles**:
- Implements infrastructure ports defined in domain
- Handles external HTTP calls, message queues, etc.
- Uses Spring WebClient and WebFlux for async operations
- No business logic - only technical concerns

**Dependencies**: `domain`, `spring-boot-starter-webclient`, `spring-boot-starter-webflux`

**Package Structure**:
```
hu.ps.ss.infra/
  ├── client/       # HTTP clients for external services
  ├── adapter/      # Port implementations
  └── config/       # Infrastructure configurations
```

**What to put here**:
- WebClient configurations and clients
- External API integrations
- Message queue adapters
- File system adapters
- Caching implementations

### 4. `api-service/` - Application and Presentation Layer
**Purpose**: REST API, application orchestration, and main entry point

**Key Principles**:
- **ONLY BOOTABLE MODULE**: Contains @SpringBootApplication
- Orchestrates use cases using domain and adapters
- Exposes REST APIs
- Handles HTTP concerns (request/response mapping, validation)
- Component scanning includes: `hu.ps.ss.apiservice`, `hu.ps.ss.data`, `hu.ps.ss.infra`

**Dependencies**: `domain`, `data`, `infra`, `spring-boot-starter-web`

**Package Structure**:
```
hu.ps.ss.apiservice/
  ├── WebSmartStorageApplication.java  # Main entry point
  ├── controller/   # REST controllers
  ├── dto/          # Data Transfer Objects (request/response)
  ├── usecase/      # Application use cases (orchestration)
  ├── mapper/       # DTO ↔ Domain mappers
  └── config/       # Application-level configuration
```

**What to put here**:
- REST controllers (@RestController)
- Request/Response DTOs
- Application services (@Service) that orchestrate use cases
- Exception handlers (@ControllerAdvice)
- Security configurations
- API documentation (Swagger/OpenAPI)

## Architecture Principles

### Dependency Flow
```
┌─────────────┐
│   domain    │ ← Core (no dependencies on other layers)
└──────┬──────┘
       │
┌──────┴──────┬──────────────┐
│             │              │
┌───▼────┐   ┌───▼────┐    ┌───▼────┐
│  data  │   │ infra  │    │  ...   │ ← Adapters depend on domain
└───┬────┘   └───┬────┘    └────────┘
    │            │
    └────┬───────┘
         │
    ┌────▼─────────┐
    │ api-service  │ ← Application layer depends on all
    └──────────────┘
```

### Key Rules
1. **Domain is King**: All business logic lives in the domain module
2. **Dependency Direction**: Always point inward toward domain, never outward
3. **Port-Adapter Pattern**: Use interfaces (ports) in domain, implementations (adapters) in infra/data
4. **Single Responsibility**: Each module has ONE clear responsibility
5. **Framework Independence**: Domain layer has ZERO framework dependencies

## Development Guidelines

### Adding New Features

#### 1. Domain-First Approach
Always start with the domain:
```java
// 1. Define domain model in domain/model/
public class StorageItem {
    private String id;
    private String name;
    // ... business logic methods
}

// 2. Define domain service interface (port)
public interface StorageRepository {
    StorageItem save(StorageItem item);
    Optional<StorageItem> findById(String id);
}
```

#### 2. Implement Adapters
```java
// data/ - JPA implementation
@Repository
public class JpaStorageRepository implements StorageRepository {
    // Implementation using Spring Data JPA
}

// infra/ - External service implementation
@Component
public class ExternalStorageClient implements ExternalStoragePort {
    // Implementation using WebClient
}
```

#### 3. Expose via API
```java
// api-service/controller/
@RestController
@RequestMapping("/api/storage")
public class StorageController {
    private final StorageService storageService;
    // REST endpoints
}
```

### Code Organization Best Practices

1. **Package by Feature, Not Layer**: Within each module, organize by feature
   ```
   domain/
     └── storage/
         ├── StorageItem.java
         ├── StorageRepository.java
         └── StorageService.java
   ```

2. **Use Meaningful Names**: 
   - Entities: Nouns (StorageItem, User)
   - Services: Verb + Noun (StorageService, UserManager)
   - Repositories: Noun + Repository (StorageRepository)

3. **Keep Classes Small**: Aim for single responsibility
   - Controllers: HTTP concerns only
   - Services: Orchestration only
   - Repositories: Data access only

4. **Use DTOs in API Layer**: Never expose domain entities directly via REST
   ```java
   // api-service/dto/
   public record StorageItemRequest(String name, String content) {}
   public record StorageItemResponse(String id, String name) {}
   ```

### Testing Strategy

#### Unit Tests
- **domain/**: Test business logic in isolation (no Spring context)
- **data/**: Use @DataJpaTest for repository tests
- **infra/**: Mock external services
- **api-service/**: Use @WebMvcTest for controller tests

#### Integration Tests
- Place in `api-service/src/test/`
- Use @SpringBootTest for end-to-end tests
- Use H2 in-memory database for testing

### Common Gradle Commands

```bash
# Build entire project
./gradlew build

# Build specific module
./gradlew :domain:build
./gradlew :data:build
./gradlew :infra:build
./gradlew :api-service:build

# Run application
./gradlew :api-service:bootRun

# Run tests
./gradlew test                    # All tests
./gradlew :domain:test           # Module-specific tests

# Clean and rebuild
./gradlew clean build

# View project structure
./gradlew projects

# View dependencies
./gradlew :api-service:dependencies
```

## Configuration Management

### Application Properties
Located in `api-service/src/main/resources/application.properties`

**Current Configuration**:
- Application name: `web-smart-storage`
- Server port: 8080 (default)
- Database: H2 in-memory (auto-configured)

### Environment-Specific Configuration
Use Spring profiles for different environments:
```properties
# application.properties (default)
spring.application.name=web-smart-storage

# application-dev.properties
spring.h2.console.enabled=true

# application-prod.properties
spring.datasource.url=jdbc:postgresql://...
```

## Common Tasks and Patterns

### Adding a New REST Endpoint

1. **Create DTO** (api-service/dto/):
   ```java
   public record CreateItemRequest(String name, String content) {}
   ```

2. **Add Domain Logic** (domain/):
   ```java
   public class StorageItem {
       public static StorageItem create(String name, String content) {
           // Business validation and creation logic
       }
   }
   ```

3. **Add Controller Endpoint** (api-service/controller/):
   ```java
   @PostMapping
   public ResponseEntity<ItemResponse> createItem(@RequestBody CreateItemRequest request) {
       // Orchestrate use case
   }
   ```

### Adding a New Database Entity

1. **Domain Model First** (domain/):
   ```java
   public class StorageItem {
       private String id;
       private String name;
       // Domain logic
   }
   ```

2. **JPA Entity** (data/entity/):
   ```java
   @Entity
   @Table(name = "storage_items")
   public class StorageItemEntity {
       @Id
       private String id;
       private String name;
       // JPA mappings
   }
   ```

3. **Repository** (data/repository/):
   ```java
   public interface StorageItemRepository extends JpaRepository<StorageItemEntity, String> {
   }
   ```

4. **Mapper** (data/mapper/):
   ```java
   public class StorageItemMapper {
       public static StorageItem toDomain(StorageItemEntity entity) { }
       public static StorageItemEntity toEntity(StorageItem domain) { }
   }
   ```

### Adding External Service Integration

1. **Define Port in Domain** (domain/):
   ```java
   public interface ExternalStoragePort {
       void uploadFile(String filename, byte[] content);
   }
   ```

2. **Implement in Infra** (infra/client/):
   ```java
   @Component
   public class ExternalStorageClient implements ExternalStoragePort {
       private final WebClient webClient;
       
       @Override
       public void uploadFile(String filename, byte[] content) {
           // WebClient implementation
       }
   }
   ```

3. **Use in Application Layer** (api-service/):
   ```java
   @Service
   public class FileUploadService {
       private final ExternalStoragePort storagePort;
       // Use the port
   }
   ```

## Technology Stack Details

### Core Dependencies
- **Java 25**: Latest LTS version with modern language features
- **Spring Boot 4.0.2**: Latest Spring Boot with enhanced performance
- **Gradle 8.14**: Minimum version required for Spring Boot 4.0.2
- **Lombok**: Reduces boilerplate (use @Data, @Builder, @Value)

### Spring Boot Starters Used
- `spring-boot-starter-web`: REST API support
- `spring-boot-starter-data-jpa`: Database access
- `spring-boot-starter-webclient`: HTTP client
- `spring-boot-starter-webflux`: Reactive web support
- `spring-boot-devtools`: Development-time features

### Testing Dependencies
- JUnit 5 (Jupiter): Unit testing
- Spring Boot Test: Integration testing
- H2 Database: In-memory testing database

## Coding Standards

### Java Style
- Use Java records for immutable DTOs
- Prefer composition over inheritance
- Use Optional for nullable returns
- Use var for local variables when type is obvious

### Spring Annotations
- `@RestController` for REST endpoints
- `@Service` for business services
- `@Repository` for data access (Spring Data auto-implements)
- `@Component` for general Spring beans

### Lombok Usage
- `@Data` for mutable data classes
- `@Value` for immutable classes
- `@Builder` for complex object creation
- `@RequiredArgsConstructor` for dependency injection

### Exception Handling
- Create domain-specific exceptions in `domain/exception/`
- Handle exceptions globally in api-service using @ControllerAdvice
- Use appropriate HTTP status codes in REST responses

## Performance Considerations

1. **Use WebClient for External Calls**: Non-blocking, reactive HTTP client
2. **Database Connection Pooling**: HikariCP (auto-configured by Spring Boot)
3. **Lazy Loading**: Be careful with JPA relationships
4. **Caching**: Use Spring Cache abstraction when needed
5. **Async Processing**: Use @Async for long-running operations

## Security Best Practices

1. **Input Validation**: Validate all inputs at API layer
2. **Never Trust User Input**: Sanitize data before processing
3. **Use DTOs**: Never expose domain entities directly
4. **Dependency Scanning**: Keep dependencies up-to-date
5. **Environment Variables**: Use for sensitive configuration

## Troubleshooting

### Common Issues

**Port 8080 Already in Use**:
```bash
# Find and kill process using port 8080
lsof -ti:8080 | xargs kill
```

**Gradle Daemon Issues**:
```bash
./gradlew --stop
```

**Clean Build**:
```bash
./gradlew clean build --refresh-dependencies
```

**H2 Console Access** (if enabled):
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (empty)

## References and Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design](https://martinfowler.com/tags/domain%20driven%20design.html)
- [Gradle Documentation](https://docs.gradle.org/)

## Quick Start for New Developers

1. **Clone and Build**:
   ```bash
   git clone <repository-url>
   cd web-smart-storage
   ./gradlew build
   ```

2. **Run Application**:
   ```bash
   ./gradlew :api-service:bootRun
   ```

3. **Verify Application**:
   ```bash
   curl http://localhost:8080/
   ```

4. **Run Tests**:
   ```bash
   ./gradlew test
   ```

## Project Maturity and Future Work

**Current State**: Foundation complete with multi-module structure
**Next Steps**:
- Add concrete domain models and business logic
- Implement actual REST endpoints
- Add comprehensive test coverage
- Configure production database (PostgreSQL/MySQL)
- Add API documentation (OpenAPI/Swagger)
- Implement security (Spring Security)
- Add observability (metrics, logging, tracing)

---

**Last Updated**: 2026-02-01  
**Maintainer**: GitHub Copilot Guidelines for aperger/web-smart-storage
