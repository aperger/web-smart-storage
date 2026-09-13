# GitHub Copilot Instructions for Web Smart Storage

## Project Overview

Web Smart Storage is the Spring Boot backend of a full port of **QtSmartStorage** (a mature
C++/Qt desktop invoicing application) plus its Hungarian **NAV Online
Invoice** integration. The backend is a multi-module **Maven** project built with strict
**hexagonal architecture** (ports and adapters). A separate Angular + Ionic PWA frontend
(not part of this repo yet) will consume the REST API.

- **Build Tool**: Maven (root `pom.xml`, `packaging=pom`), **not Gradle**
- **Language**: Java 25 · **Framework**: Spring Boot 4.1.1
- **Persistence**: PostgreSQL, MySQL and SQLite must all be supported (see
  `api-service/src/main/resources/application-{pgsql,mysql,sqlite}.yml` and the dialect/naming
  classes under `api-service/.../config`). SQLite/legacy schemas can have "sticky" quirky column
  names (see `@Column(name=...)` in entities) — never rename DB columns casually.
- **Mapping**: MapStruct · **Boilerplate**: Lombok · **Docs**: springdoc-openapi

## Module Structure (dependency direction is inward, toward `domain`)

```
online-invoice-xml → domain → data → infra → api-service
                        ↑        (data, infra both depend only on domain)
```

1. **`domain/`** — pure Java, **zero Spring dependencies**. Only Lombok.
   - `hu.ps.ss.domain.<Xxx>Model` — domain models (e.g. `CountryModel`), usually extend
     `hu.ps.ss.domain.pojo.ItemWithIdEditable` (id + `modified`/`modifiedBy`) or the lighter
     `ItemWithId` / `ItemEditable`.
   - `hu.ps.ss.domain.pojo` — shared value types: `ItemWithId`, `ItemEditable`,
     `ItemWithIdEditable`, `PageResult<T>` (record: `items`, `pageDetails`), `PageDetails` (record).
   - `hu.ps.ss.domain.ports.basic` — CRUD ports for simple reference/master data, all extend
     `BaseModelPort<M, I>` (`findAll`, `findById`, `search(...)` paginated/list, `save`,
     `deleteById`, `delete`). One-liner port per entity, e.g.
     `interface VatKeyEditorPort extends BaseModelPort<VatKeyModel, Integer> {}`.
   - `hu.ps.ss.domain.ports.document` — ports for the invoicing/document domain and external
     integrations (`DocumentEditorPort`, `OnlineInvoiceApiPort`). These are richer than simple CRUD.
2. **`data/`** — JPA entities + MapStruct mapper contracts (framework-light: JPA + Lombok +
   MapStruct only, no repositories/services here).
   - `hu.ps.ss.data.entity` — `@Entity` classes, mostly extending `EntityBase` (→ `IdentifierBase`)
     which carries `modified`/`modifiedBy`. Table/column names mirror the legacy QtSmartStorage
     schema (e.g. `TORSZAGOK`/`FKOD`/`FNEV`) — keep them unless a migration is explicitly planned.
   - `hu.ps.ss.data.mappers` — the **mandatory mapper base hierarchy** (use everywhere, no
     exceptions):
     - `ObjectMapperOneWay<S, D>` — `D map(S)`, `List<D> mapList(List<S>)`
     - `ObjectMapperBase<S, D>` extends OneWay — adds `S parseFrom(D)` (read-back direction)
     - `ObjectMapper<S, D>` extends Base — adds `updateSource`/`updateDest` (in-place updates)
     - `CommonMapperConfig` — the shared `@MapperConfig` (Spring component model, constructor
       injection, `ERROR` on unmapped targets, adder-preferred collections). **Every**
       `@Mapper` interface in `data` and `infra`/`api-service` must set `config = CommonMapperConfig.class`.
3. **`infra/`** — the adapter/implementation layer for **all** ports (both persistence and
   external integrations). Depends on `domain` + `data`.
   - `hu.ps.ss.infra.database.repository` — Spring Data JPA repos extending
     `BaseRepository<T, ID>` (`JpaRepository` + `JpaSpecificationExecutor`).
   - `hu.ps.ss.infra.database.mapper` — `@Mapper(config = CommonMapperConfig.class)` interfaces
     extending `ObjectMapperBase<Entity, Model>` (entity ↔ domain model).
   - `hu.ps.ss.infra.database.service` — `AbstractEntityService<E, M, ID>` generic CRUD/search
     engine (pagination, sorting, `Specification`-based filtering); one thin subclass per entity
     implementing `getEntityClass()` and `createSearchSpecification(...)`.
   - `hu.ps.ss.infra.database` (adapters) — `AbstractModelAdapter<E, M, I>` implements
     `BaseModelPort<M, I>` generically by delegating to the entity service; one thin
     `@Service class XxxEditorAdapter extends AbstractModelAdapter<...> implements XxxEditorPort`
     per entity — **do not re-implement CRUD logic in the adapter**.
   - `hu.ps.ss.infra.onlineinvoice` — NAV Online Invoice v3 adapters implementing
     `OnlineInvoiceApiPort`, built on the JAXB classes generated in `online-invoice-xml`.
4. **`api-service/`** — **the only bootable module** (`ApiServiceApplication`,
   `@SpringBootApplication`). Depends on `domain`, `data`, `infra`.
   - `hu.ps.ss.apiservice.dto` — API DTOs, extend the same `ItemWithId(Editable)` base classes
     as domain models, annotated with `@Value`/`@Schema` (springdoc). Suffix: `...Dto`.
   - `hu.ps.ss.apiservice.mapper` — `@Mapper(config = CommonMapperConfig.class)` interfaces
     extending `ObjectMapperBase<Model, Dto>` (domain model ↔ DTO).
   - `hu.ps.ss.apiservice.controller` — `AbstractModelController<M, D, S extends
     BaseModelPort<M, Integer>>` provides generic `search`, `getItemById`, `createItem`,
     `deleteById`. One thin `@RestController class XxxEditorController extends
     AbstractModelController<...>` per entity that just adds `@RequestMapping`, OpenAPI
     annotations (`@Tag`, `@Operation`, `@ApiResponses`) and delegates to `super`.
   - `hu.ps.ss.apiservice.config` — per-DB dialect/naming strategy classes
     (`CustomPostgreSQLDialect`, `CustomSQLiteDialect`, `MySqlUpperCaseNamingStrategy`,
     `QuotedNamingStrategyMySql`) and shared config (`ObjectMapperConfiguration`,
     `PasswordEncoderConfig`).
5. **`online-invoice-xml/`** — JAXB-generated classes (from the official NAV `osa_schemas_v3`
   XSDs under `src/main/resources/xsd`) plus custom date adapters. Framework-independent; used
   by `infra.onlineinvoice` to build/parse NAV requests and responses. **Never hand-edit
   generated JAXB sources** — regenerate from the XSDs/`global.xjb` binding instead.

## The canonical vertical slice (reference entity: `Country`)

`CountryEntity` (data) → `CountryMapper` (infra, entity↔model) → `CountryRepository` (infra) →
`CountryEntityService` (infra) → `CountryEditorAdapter` (infra) implements `CountryEditorPort`
(domain) ← `CountryEditorMapper` (api-service, model↔dto) ← `CountryDto` (api-service) ←
`CountryEditorController` (api-service). Copy this pattern exactly for every new simple
reference entity; only diverge for aggregates that need custom ports (`ports.document`).

## Multi-database support rules

- Never assume one dialect. Validate new JPA mappings/queries against `application-dev-pgsql.yml`,
  `application-dev-mysql.yml` and `application-dev-sqlite.yml` profiles.
- Respect existing (sometimes legacy/"sticky") column and table names via `@Column`/`@Table`;
  do not normalize them unless asked.
- SQLite has limited type/constraint support — avoid DB-specific SQL/JPQL features that don't
  degrade gracefully across all three engines; prefer `Specification`-based filtering (already
  used by `AbstractEntityService`) over native queries when possible.

## Common Maven commands

```bash
mvn -q -pl domain,data,infra,api-service -am compile   # compile all modules
mvn -q -pl domain test                                  # module-specific tests
mvn -q test                                              # full test suite
mvn -q -pl api-service spring-boot:run                   # run the app (H2 by default)
mvn -q -pl api-service spring-boot:run -Dspring-boot.run.profiles=dev-sqlite
```

## Using AI agents for this port

This repository follows the same Copilot methodology as other projects by this maintainer:
project-wide instructions (this file) → a **skill** describing the phased workflow → focused
**custom agents**, one per hexagonal layer, each with a narrow job and a code template.

- Skill: `entity-vertical-slice` (`.github/skills/entity-vertical-slice/SKILL.md`) — the
  step-by-step workflow for porting one QtSmartStorage feature end-to-end.
- Agents (`.github/agents/`):
  - `@domain-model-agent` — domain models, pojos, ports
  - `@persistence-adapter-agent` — `data` entities/mappers + `infra` repository/service/adapter
  - `@api-layer-agent` — DTOs, api-service mappers, controllers
  - `@nav-online-invoice-agent` — NAV Online Invoice XML/token/signing integration
  - `@vertical-slice-kickoff` — coordinator that sequences the above agents per feature/entity

Always start new feature work with the skill, then delegate layer-by-layer with the matching
agent. Keep each agent invocation scoped to one layer and one entity/feature at a time.

## Coding standards

- Use Lombok consistently: `@Data`/`@Value` + `@Builder` + `@NoArgsConstructor`/
  `@AllArgsConstructor`; add `@EqualsAndHashCode(callSuper = true)`/`@ToString(callSuper = true)`
  whenever extending a base class with fields.
- Use MapStruct (`data.mappers` hierarchy) for **every** object mapping — never hand-write
  entity↔model↔dto conversions.
- Prefer `Optional` for nullable single-item returns; use `var` when the type is obvious.
- Never expose JPA entities or domain models directly via REST — always go through a DTO.
- Business rules belong in `domain`; controllers/adapters/services stay thin and delegate.

## Testing strategy

- `domain/`: plain JUnit 5, no Spring context.
- `data/`+`infra/`: `@DataJpaTest`/`@SpringBootTest` against H2 first, then re-validate against
  the pgsql/mysql/sqlite profiles for anything dialect-sensitive.
- `api-service/`: `@WebMvcTest` for controllers, `@SpringBootTest` for end-to-end flows.
- NAV integration: mock the SOAP/REST/XML boundary; never hit the real NAV endpoints from tests.

## Project maturity and next steps

**Done**: multi-module skeleton, mapper/port/adapter/controller base classes, `Country` fully
wired end-to-end, several entities partially wired (`VatKey` has data/infra but no api-service
layer yet; `Currency`, `ItemGroup`, `ItemType`, `PaymentMethod`, `Partner`, `MasterItem` have
domain ports/models only).

**Next steps** (see the skill for the phased plan): finish simple reference entities end-to-end,
then Partner/MasterItem aggregates, then the Document/invoice aggregate, then NAV Online Invoice
submission using `online-invoice-xml`.

## Git commit conventions

- Do **not** add a `Co-authored-by: Copilot App ...` trailer to commit messages in this
  repository. All commits should be authored solely as the user (via the locally configured
  git identity), with no Copilot co-author trailer.

---
**Maintainer**: GitHub Copilot Guidelines for aperger/web-smart-storage
