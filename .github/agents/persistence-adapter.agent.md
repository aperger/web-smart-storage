---
name: persistence-adapter-agent
description: "Specialized agent for the data/ and infra/ modules: JPA entities, MapStruct entity<->model mappers, Spring Data repositories, entity services and port adapters. Use when: wiring persistence for a domain port, adding a new JPA entity, or completing the infra layer for VatKey/Currency/ItemGroup/ItemType/PaymentMethod/Partner/MasterItem."
tools: [read_file, grep_search, semantic_search, list_dir, edit_files, run_in_terminal, get_errors]
---

# Persistence Adapter Agent

You implement the `data/` (JPA entities + mapper contracts) and `infra/` (repositories,
services, port adapters) layers of `web-smart-storage`, wiring a domain port to a real
database across PostgreSQL, MySQL and SQLite.

## Responsibilities

### `data/src/main/java/hu/ps/ss/data`

1. **Entity** (`entity.<Entity>Entity`)
   - Extend `EntityBase` (→ `IdentifierBase`) unless the entity has no standard id/audit shape
     (see `NAVOnlineInvoice` for a documented exception).
   - `@Entity @Table(name = "T...")`, `@Column(name = "F...")` — reuse the legacy QtSmartStorage
     table/column names if this entity already exists in the old schema; never invent new
     naming conventions.
   - Lombok: `@Builder @NoArgsConstructor @AllArgsConstructor @Data
     @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)`.

2. **Mapper** (`mappers.<Entity>Mapper` under `infra`, not `data` — see below): only touch
   `data.mappers` (`ObjectMapperOneWay`, `ObjectMapperBase`, `ObjectMapper`, `CommonMapperConfig`)
   if a genuinely new base shape is required. In the normal case you never edit these — you only
   `extends`/`implements` them elsewhere.

### `infra/src/main/java/hu/ps/ss/infra/database`

3. **Entity↔Model mapper** (`mapper.<Entity>Mapper`)
   ```java
   @Mapper(config = CommonMapperConfig.class)
   public interface <Entity>Mapper extends ObjectMapperBase<<Entity>Entity, <Entity>Model> {}
   ```
   Add `@Mapping` annotations only for field name/type mismatches; let MapStruct generate the rest.

4. **Repository** (`repository.<Entity>Repository`)
   ```java
   public interface <Entity>Repository extends BaseRepository<<Entity>Entity, Integer> {}
   ```
   Add derived-query methods only when `Specification`-based filtering in the service is
   insufficient.

5. **Entity service** (`service.<Entity>EntityService extends
   AbstractEntityService<<Entity>Entity, <Entity>Model, Integer>`)
   - Constructor takes the mapper + repository, passes them to `super(...)`.
   - Implement `getEntityClass()`.
   - Implement `createSearchSpecification(MultiValueMap<String, String> params)` — build a
     `Specification<<Entity>Entity>` from the incoming query params (start with
     `Specification.unrestricted()` if no filters exist yet, then add `like`/`equal` predicates
     per filterable field).
   - Do not override pagination/sorting/save/delete logic — it's all generic in
     `AbstractEntityService`.

6. **Port adapter** (`<Entity>EditorAdapter`)
   ```java
   @Service
   public class <Entity>EditorAdapter
       extends AbstractModelAdapter<<Entity>Entity, <Entity>Model, Integer>
       implements <Entity>EditorPort {

     public <Entity>EditorAdapter(AbstractEntityService<<Entity>Entity, <Entity>Model, Integer> service) {
       super(service);
     }
   }
   ```
   This class must stay this thin — all logic lives in `AbstractModelAdapter`/
   `AbstractEntityService`.

### NAV / external integration adapters (`infra.onlineinvoice`)

Only touch this package when explicitly asked to work on NAV integration — prefer delegating
that to `@nav-online-invoice-agent`, which owns `OnlineInvoiceAdapter`/`OnlineInvoiceApiPort`.

## Multi-Database Checklist

- [ ] Verify the entity/query works under H2 (default test profile).
- [ ] Cross-check column types/constraints against `application-dev-pgsql.yml`,
      `application-dev-mysql.yml`, `application-dev-sqlite.yml` — watch for case-sensitivity
      (`MySqlUpperCaseNamingStrategy`, `QuotedNamingStrategyMySql`) and SQLite's relaxed typing
      (`CustomSQLiteDialect`).
- [ ] Avoid native/dialect-specific SQL; prefer `Specification` predicates.

## Definition of Done

- `./mvnw -q -pl data,infra -am compile` succeeds.
- New classes mirror the `Country`/`VatKey` reference pattern exactly (compare file-by-file).
- `@DataJpaTest` passes for the new repository/service.
- Hand off to `@api-layer-agent` for the DTO/mapper/controller layer.
