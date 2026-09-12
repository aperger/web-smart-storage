# Web Smart Storage

Web Smart Storage is the Spring Boot backend of a full port of **QtSmartStorage** (a mature C++/Qt desktop invoicing and stock-management application) plus its Hungarian **NAV Online Invoice v3.0** integration.

The backend is built as a multi-module **Maven** project following strict **Hexagonal Architecture** (ports and adapters) principles, supporting **PostgreSQL**, **MySQL**, and **SQLite** databases.

---

## Technology Stack

- **Java**: 25 (LTS)
- **Framework**: Spring Boot 4.0.3
- **Build Tool**: Maven (`./mvnw`)
- **Mapping**: MapStruct (with strict mapper hierarchy)
- **Boilerplate Reduction**: Lombok
- **API Documentation**: springdoc-openapi (Swagger v3)
- **Databases Supported**: PostgreSQL, MySQL, SQLite (plus H2 for fast testing)

---

## Module Structure & Hexagonal Architecture

Dependency direction flows **inward** toward `domain`:

```
online-invoice-xml ──┐
                     ├──> domain <─── data
                     │      ▲
                     │      │
                     ├─── infra
                     │
               api-service
```

### 1. `domain/` — Core Domain Layer
- **Purpose**: Pure business models and ports.
- **Key Principles**:
  - **Zero Spring dependencies** — pure Java and Lombok only.
  - Contains domain models (`hu.ps.ss.domain.<Entity>Model`), value objects, and domain ports (`hu.ps.ss.domain.ports.basic.BaseModelPort`).
- **Package Structure**:
  - `hu.ps.ss.domain` — Domain models (e.g. `CountryModel`, `VatKeyModel`).
  - `hu.ps.ss.domain.pojo` — Shared value types (`ItemWithId`, `ItemEditable`, `ItemWithIdEditable`, `PageResult<T>`, `PageDetails`).
  - `hu.ps.ss.domain.ports.basic` — CRUD ports (`BaseModelPort<M, I>`, `CountryEditorPort`).
  - `hu.ps.ss.domain.ports.document` — Invoicing & integration ports (`DocumentEditorPort`, `OnlineInvoiceApiPort`).

### 2. `data/` — JPA Entities & Mapping Contracts
- **Purpose**: Database schema mappings and base MapStruct interfaces.
- **Package Structure**:
  - `hu.ps.ss.data.entity` — JPA `@Entity` classes (extending `EntityBase` → `IdentifierBase`), retaining legacy QtSmartStorage database table and column names (`TORSZAGOK`, `FKOD`, `FNEV`).
  - `hu.ps.ss.data.mappers` — Base MapStruct contracts (`ObjectMapperOneWay`, `ObjectMapperBase`, `ObjectMapper`, `CommonMapperConfig`).

### 3. `infra/` — Persistence & Infrastructure Adapters
- **Purpose**: Adapter implementations for domain ports, database repositories, entity services, and external API integrations.
- **Package Structure**:
  - `hu.ps.ss.infra.database.repository` — Spring Data JPA repositories extending `BaseRepository<T, ID>`.
  - `hu.ps.ss.infra.database.mapper` — MapStruct entity ↔ domain model mappers (`CountryMapper`).
  - `hu.ps.ss.infra.database.service` — Generic CRUD engine `AbstractEntityService<E, M, ID>` providing specification filtering, pagination, and sorting.
  - `hu.ps.ss.infra.database` — Domain port adapters (`AbstractModelAdapter<E, M, I>`, `CountryEditorAdapter`).
  - `hu.ps.ss.infra.onlineinvoice` — NAV Online Invoice v3 integration adapters (`OnlineInvoiceAdapter`).

### 4. `api-service/` — Application & REST Presentation Layer
- **Purpose**: Exposes REST endpoints, application DTOs, and serves as the **only bootable module** (`ApiServiceApplication`).
- **Package Structure**:
  - `hu.ps.ss.apiservice.controller` — REST controllers extending `AbstractModelController<M, D, S>` with OpenAPI annotations.
  - `hu.ps.ss.apiservice.dto` — Request/Response DTOs extending `ItemWithIdEditable` with springdoc `@Schema` annotations.
  - `hu.ps.ss.apiservice.mapper` — MapStruct domain model ↔ DTO mappers (`CountryEditorMapper`).
  - `hu.ps.ss.apiservice.config` — Multi-database dialects, naming strategies, and Spring security configurations.

### 5. `online-invoice-xml/` — JAXB XML Binding Layer
- **Purpose**: JAXB classes generated from official NAV `osa_schemas_v3` XSDs for building and parsing Hungarian NAV Online Invoice XML payloads.

---

## Architectural Principles & Rules

1. **Domain Isolation**: Business logic lives exclusively in `domain/` without framework annotations.
2. **Dependency Inversion**: Adapters in `infra/` and `data/` depend on interfaces (ports) declared in `domain/`.
3. **Mandatory Mapper Hierarchy**: All MapStruct interfaces in `data`, `infra`, and `api-service` must use `@Mapper(config = CommonMapperConfig.class)` and extend `ObjectMapperBase` or `ObjectMapper`.
4. **No Direct Entity Exposure**: REST controllers never return JPA entities or domain models directly; all API communication uses DTOs.
5. **Legacy Database Column Preservations**: Sticky table and column names (`FKOD`, `FNEV`, `FIDO`, `FUSER`) map to legacy QtSmartStorage database structures across PostgreSQL, MySQL, and SQLite.

---

## Canonical Vertical Slice Pattern (`Country`)

For every reference data entity in the application, follow the `Country` pattern verbatim:

```
CountryEntity (data)
    └── CountryMapper (infra)
    └── CountryRepository (infra)
    └── CountryEntityService (infra)
    └── CountryEditorAdapter (infra) ──implements──> CountryEditorPort (domain)
                                                             ▲
CountryDto (api-service) <── CountryEditorMapper <── CountryEditorController (api-service)
```

---

## Building and Running

### Maven Commands

Compile all modules:
```bash
./mvnw -q -pl domain,data,infra,api-service -am compile
```

Run tests:
```bash
./mvnw -q test                     # All modules
./mvnw -q -pl domain test          # Domain module only
./mvnw -q -pl infra test           # Infra module only
```

Run application (H2 default):
```bash
./mvnw -q -pl api-service spring-boot:run
```

Run application with specific database profile:
```bash
# SQLite
./mvnw -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-sqlite

# PostgreSQL
./mvnw -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-pgsql

# MySQL
./mvnw -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-mysql
```

---

## Testing Strategy

- **`domain/`**: Fast unit tests with JUnit 5 (zero Spring context).
- **`data/` & `infra/`**: `@DataJpaTest` / `@SpringBootTest` repository and service tests against H2, verified against PostgreSQL/MySQL/SQLite profiles.
- **`api-service/`**: `@WebMvcTest` for REST controllers and `@SpringBootTest` for end-to-end API integration tests.
- **NAV Integration**: Mock the SOAP/REST XML boundary; never invoke real NAV endpoints from tests.


