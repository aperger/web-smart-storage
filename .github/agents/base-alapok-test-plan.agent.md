---
name: base-alapok-test-plan
description: "Handoff note for continuing Base/Alapok test completion and validation-gate enforcement from another GitHub account/session."
---

# Base/Alapok Test Completion Handoff

## Goal

Continue the `vertical-slice-kickoff` / `entity-vertical-slice` Phase 1 work for the Base/Alapok
menu by adding missing automated tests and enforcing validation gates per entity, while keeping
Document editing deferred.

## Planned entity order

1. `Country`
2. `VatKey`
3. `Currency`
4. `ItemGroup`
5. `ItemType`
6. `PaymentMethod`
7. `Storage`

## Current progress

Work started on **Country** as the baseline entity.

### Done

- Read all `.github/agents/*.agent.md` files and the related
  `.github/skills/entity-vertical-slice/SKILL.md`.
- Confirmed the repo currently has **no test source files** under:
  - `domain/src/test`
  - `infra/src/test`
  - `api-service/src/test`
- Confirmed current Base entity services/controllers already exist, but most service search specs
  still return `Specification.unrestricted()`.
- Implemented real filtering in:
  - `infra/src/main/java/hu/ps/ss/infra/database/service/CountryEntityService.java`
    - supports `code` and `name` filters
- Added first test files:
  - `infra/src/test/java/hu/ps/ss/infra/database/service/CountryEntityServiceTest.java`
  - `infra/src/test/resources/application-test.yml`
  - `api-service/src/test/java/hu/ps/ss/apiservice/controller/CountryEditorControllerTest.java`
- Added `infra` test dependencies to support repository/service tests:
  - `spring-boot-starter-data-jpa-test`
  - `spring-boot-test-autoconfigure`
  - `spring-test`

## Important blocker discovered

While implementing the Country baseline, a **shared mapping problem** was exposed:

- `infra` MapStruct mappers for entity ↔ domain model do **not** propagate inherited fields from
  the base classes:
  - `id`
  - `modified`
  - `modifiedBy`
- Result: saving via `AbstractEntityService.saveItem(...)` returns models with `id=0`, because the
  generated mapper ignores inherited fields when Lombok `@Builder` is used on subclasses.

### Why this happens

Generated mapper code showed:
- `api-service` model → DTO mapping already sets inherited fields explicitly on the DTO instance
- but `infra` entity ↔ model mapping uses Lombok subclass builders, and those builders **do not
  expose inherited fields**

This is a **shared root-cause issue**, not a Country-only issue.

## Approved direction

The user explicitly approved the proper fix:

> apply the shared `@SuperBuilder` fix first, then continue the Country baseline and the rest

## Current partial changes related to the blocker

These files were edited while investigating the inherited-field issue:

- `data/src/main/java/hu/ps/ss/data/mappers/CommonMapperConfig.java`
  - added `mappingInheritanceStrategy = AUTO_INHERIT_ALL_FROM_CONFIG`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/CountryMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/VatKeyMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/CurrencyMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/ItemGroupMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/ItemTypeMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/PaymentMethodMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/StorageMapper.java`

Those explicit inherited-field `@Mapping(...)` changes currently **do not compile** because
Lombok subclass `@Builder` does not generate builder setters for superclass fields.

## Recommended next steps

### 1. Fix the shared builder/mapping issue first

Apply Lombok `@SuperBuilder` to the shared inheritance chains that need inherited fields mapped:

- Domain side:
  - `domain/.../pojo/ItemWithId`
  - `domain/.../pojo/ItemWithIdEditable`
  - any Base/Alapok models extending them (`CountryModel`, `VatKeyModel`, `CurrencyModel`,
    `ItemGroupModel`, `ItemTypeModel`, `PaymentMethodModel`, `StorageModel`)
- Data side:
  - `data/.../entity/IdentifierBase`
  - `data/.../entity/EntityBase`
  - Base/Alapok entities extending them (`CountryEntity`, `VatKeyEntity`, `CurrencyEntity`,
    `ItemGroupEntity`, `ItemTypeEntity`, `PaymentMethodEntity`, `StorageEntity`)

Then regenerate/compile MapStruct and verify generated `infra` mappers now include:
- `id`
- `modified`
- `modifiedBy`

### 2. Re-run Country baseline

After the `@SuperBuilder` fix:

1. compile:
   - `mvn -q -pl api-service -am compile`
2. run Country infra test:
   - `mvn -q -pl infra -am -Dsurefire.failIfNoSpecifiedTests=false -Dtest=CountryEntityServiceTest test`
3. run Country API test:
   - `mvn -q -pl api-service -am -Dsurefire.failIfNoSpecifiedTests=false -Dtest=CountryEditorControllerTest test`

Expected outcome:
- Country search spec works
- Country save test returns a populated id
- Country controller tests pass

### 3. Continue entity-by-entity

For each remaining entity, in order:

- implement real `createSearchSpecification(...)` filters to match controller-advertised params
- add:
  - `infra` service/repository test
  - `api-service` controller test
- run gates before moving on:
  - `mvn -q -pl domain compile`
  - `mvn -q -pl data,infra -am compile`
  - `mvn -q -pl infra test`
  - `mvn -q -pl api-service -am compile`
  - `mvn -q -pl api-service test`

## Files currently touched in this effort

- `infra/pom.xml`
- `data/src/main/java/hu/ps/ss/data/mappers/CommonMapperConfig.java`
- `infra/src/main/java/hu/ps/ss/infra/database/service/CountryEntityService.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/CountryMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/VatKeyMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/CurrencyMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/ItemGroupMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/ItemTypeMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/PaymentMethodMapper.java`
- `infra/src/main/java/hu/ps/ss/infra/database/mapper/StorageMapper.java`
- `infra/src/test/java/hu/ps/ss/infra/database/service/CountryEntityServiceTest.java`
- `infra/src/test/resources/application-test.yml`
- `api-service/src/test/java/hu/ps/ss/apiservice/controller/CountryEditorControllerTest.java`

## Validation notes from this session

- `mvn -q -pl api-service -am compile` succeeded before the explicit inherited-field mapper edits.
- After adding explicit inherited-field mappings in infra mappers, compilation failed with
  MapStruct errors like:
  - `Unknown property "id" in result type ...Builder`
  - same for `modified` and `modifiedBy`
- This confirmed the need for the `@SuperBuilder` migration.

## Commit message for this checkpoint

`test(base): fix Country controller test and shared builder inheritance`

## Scope guardrails

- Do **not** start Document editing in this phase.
- Do **not** move on to `VatKey` until Country baseline + shared mapper fix are green.
- Keep the work aligned with:
  - `.github/agents/vertical-slice-kickoff.agent.md`
  - `.github/skills/entity-vertical-slice/SKILL.md`
