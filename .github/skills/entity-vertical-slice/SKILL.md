---
name: entity-vertical-slice
description: "Port one QtSmartStorage feature (entity or aggregate) end-to-end through the web-smart-storage hexagonal layers: domain model/port, data entity + mapper, infra repository/service/adapter, api-service dto/mapper/controller. Use when: implementing a new REST-exposed entity, finishing a partially-wired entity (e.g. VatKey, Currency, ItemGroup, ItemType, PaymentMethod, Partner, MasterItem), or planning the Document/NAV Online Invoice phases."
---

# Entity Vertical Slice Implementation Skill

## Purpose

Guide the phased, layer-by-layer port of QtSmartStorage features into the Spring Boot backend,
always following the existing `Country` reference pattern and the mandatory `ObjectMapper*`
mapper hierarchy from `data.mappers`.

## When to Use

Invoke this skill by describing which phase/entity you're working on. It delegates the actual
coding to the layer-specific agents:

- `@domain-model-agent` — domain model / port
- `@persistence-adapter-agent` — data entity + infra repository/service/adapter/mapper
- `@api-layer-agent` — DTO + api-service mapper + controller
- `@nav-online-invoice-agent` — NAV Online Invoice XML integration (Phase 4 only)
- `@vertical-slice-kickoff` — coordinates all of the above for one entity/feature

## Implementation Phases

### Phase 0 — Reference pattern (DONE)
`Country` is the canonical example: `CountryEntity` → `CountryMapper` → `CountryRepository` →
`CountryEntityService` → `CountryEditorAdapter`/`CountryEditorPort` → `CountryEditorMapper` →
`CountryDto` → `CountryEditorController`. Read these seven files before implementing anything
new; every new simple reference entity must mirror this exactly.

### Phase 1 — Finish simple reference/master-data entities (read-mostly CRUD)
Entities already partially wired (check current state before starting each):
- `VatKey` — fully wired and validated in the repo; service filters are implemented and the
  corresponding infra/API tests pass.
- `Currency` — fully wired and validated in the repo; service filters are implemented and the
  corresponding infra/API tests pass.
- `ItemGroup` — fully wired and validated in the repo; service filters are implemented and the
  corresponding infra/API tests pass.
- `ItemType` — fully wired and validated in the repo; parent-child filtering by `itemGroupId`
  and `name` is implemented and the corresponding infra/API tests pass.
- `PaymentMethod` — fully wired and validated in the repo; the service filters for `name`,
  `dueDate`, and `invoiceFormat` pass the focused infra/API checks.
- `Storage` — fully wired and validated in the repo; service filters for `name` and `type`
  pass the focused infra/API checks.

For each entity in this phase, use `@vertical-slice-kickoff` with the entity name; it will call
`@persistence-adapter-agent` then `@api-layer-agent` (domain layer is usually already present —
verify with `@domain-model-agent` first and only create what's missing). For the current
Base-menu-first roadmap, use this order unless the user overrides it:
`Country` (baseline only) → `VatKey` → `Currency` → `ItemGroup` → `ItemType` →
`PaymentMethod` → `Storage`.

### Phase 2 — Partner and MasterItem aggregates
`PartnerModel`/`MasterItemModel` are richer than plain reference data (relations to
`ItemGroupEntity`, `ItemPropertyEntity`/`ItemPropertyValueEntity`, `PartnerCar`, etc.). Still
follow the same `BaseModelPort`/`AbstractModelAdapter`/`AbstractModelController` pattern, but
expect:
- Custom `createSearchSpecification(...)` filters (multi-field search).
- Nested DTOs for related entities (mirror the "id + name only" convention used for nested
  references elsewhere in this codebase).
- MapStruct sub-mappings — still route everything through `ObjectMapperBase`/`ObjectMapper` and
  `CommonMapperConfig`; use `@Mapping` and nested `uses = {...}` mappers, never manual mapping code.

### Phase 3 — Document / invoice aggregate
`DocumentHeader` + `DocumentItem` + `DocumentItemNote` + `CommentHead`/`CommentLine` form the
core invoicing aggregate. This needs a custom port under `hu.ps.ss.domain.ports.document`
(`DocumentEditorPort`, currently an empty stub) rather than `BaseModelPort`, because invoices
have state transitions (draft → issued → NAV-submitted → annulled) that don't fit generic CRUD.
- Model the aggregate root (`DocumentHeader`) with a list of line items, not flat CRUD.
- Add domain-level validation (VAT calculation, required NAV fields) as pure domain logic —
  no Spring, no persistence concerns.
- Persistence adapter still follows entity→mapper→repository→service→adapter, but the adapter
  implements the richer `DocumentEditorPort`, not `AbstractModelAdapter`'s generic contract.

### Phase 4 — NAV Online Invoice integration
Use `@nav-online-invoice-agent`. Builds on:
- `online-invoice-xml` JAXB classes generated from `osa_schemas_v3` (`invoiceApi.xsd`,
  `invoiceData.xsd`, `invoiceAnnulment.xsd`, `serviceMetrics.xsd`).
- `NAVOnlineInvoice` entity (`data/entity/NAVOnlineInvoice.java`) storing request/response XML
  blobs (`xmlInvoice`, `xmlResponse`) and NAV transaction status.
- `OnlineInvoiceApiPort` (domain, currently empty) and `OnlineInvoiceAdapter` (infra, currently
  empty) — to be filled in with token exchange, request signing (SHA3-512 password hash + SHA3-512
  request signature per NAV spec), and manageInvoice/queryInvoiceStatus/queryTransactionStatus
  operations.
- Never call the real NAV endpoint from tests; mock the WebClient/XML boundary.

## Standard Per-Entity Checklist (Phases 0–1)

### Domain (`domain/`)
- [ ] `<Entity>Model` extends `ItemWithIdEditable` (or lighter base) with plain fields + Lombok
      (`@Builder @NoArgsConstructor @AllArgsConstructor @Data @ToString(callSuper=true)
      @EqualsAndHashCode(callSuper=true)`)
- [ ] `<Entity>EditorPort extends BaseModelPort<<Entity>Model, Integer> {}` (one-liner, unless a
      richer contract is required — see Phase 2/3)
- [ ] For parent-child reference data, expose only the parent id in the child model/DTO (for
      example `ItemType.itemGroupId`) instead of nesting persistence-layer objects.

### Data (`data/`)
- [ ] `<Entity>Entity extends EntityBase` with `@Entity @Table(name=...)` mirroring the legacy
      schema, `@Builder @NoArgsConstructor @AllArgsConstructor @Data
      @EqualsAndHashCode(callSuper=true) @ToString(callSuper=true)`

### Infra (`infra/`)
- [ ] `<Entity>Mapper extends ObjectMapperBase<<Entity>Entity, <Entity>Model>` with
      `@Mapper(config = CommonMapperConfig.class)`
- [ ] `<Entity>Repository extends BaseRepository<<Entity>Entity, Integer> {}`
- [ ] `<Entity>EntityService extends AbstractEntityService<<Entity>Entity, <Entity>Model, Integer>`
      implementing `getEntityClass()` and `createSearchSpecification(...)`
- [ ] `<Entity>EditorAdapter extends AbstractModelAdapter<<Entity>Entity, <Entity>Model, Integer>
      implements <Entity>EditorPort`, `@Service`

### Api-service (`api-service/`)
- [ ] `<Entity>Dto extends ItemWithIdEditable` with `@Value @ToString(callSuper=true)
      @EqualsAndHashCode(callSuper=true) @Schema(...)` and per-field `@Schema` docs
- [ ] `<Entity>EditorMapper extends ObjectMapperBase<<Entity>Model, <Entity>Dto>` with
      `@Mapper(config = CommonMapperConfig.class)`
- [ ] `<Entity>EditorController extends AbstractModelController<<Entity>Model, <Entity>Dto,
      <Entity>EditorPort>` with `@RestController @RequestMapping("/api/v1/<entities>") @Tag(...)
      @SecurityRequirement(name = "bearerAuth")`, full OpenAPI `@Operation`/`@ApiResponses` on
      search/getById/create/delete actions (copy `CountryEditorController` verbatim and rename)

### Tests
- [ ] `domain`: model/port unit tests (no Spring context)
- [ ] `infra`: `@DataJpaTest` for repository + service; run against H2, then spot-check pgsql/
      mysql/sqlite profiles if the entity has DB-specific quirks
- [ ] `api-service`: `@WebMvcTest` for the controller (search/getById/create/delete, 404 path)

## Important Notes

- **Never** write manual entity↔model↔dto conversion code — always go through
  `ObjectMapperOneWay`/`ObjectMapperBase`/`ObjectMapper` + `CommonMapperConfig`.
- **Never** put business logic in controllers, adapters, or repositories — only in `domain`.
- **Never** rename legacy DB columns/tables without an explicit migration task.
- Keep each phase small and verified (compile + tests green) before moving to the next entity.
- Update this skill's Phase 1 status notes as entities get fully wired or verified, so the next
  session knows what is still incomplete.
