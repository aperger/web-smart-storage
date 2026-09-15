---
name: frontend-ionic-kickoff
description: "Guide for implementing the WebSmartStorage Ionic/Angular frontend under frontend/ with phased delivery, JSON-driven two-level menu, theme persistence, and ItemGroups first slice."
---

# Frontend Ionic/Angular Kickoff Skill

## Purpose

Provide a repeatable workflow for building the new frontend in `frontend/` while preserving Ionic defaults, supporting mobile/PWA usage, and aligning UI behavior with QtSmartStorage expectations.

## When to Use

Use this skill when:
- Starting frontend work in this repository.
- Continuing an unfinished frontend phase.
- Handing off frontend tasks to another contributor.

## Phase Workflow

### FE-00 — Baseline decisions
- Confirm frontend root and Angular-standard folder layout.
- Define menu JSON schema and route naming conventions.
- Confirm one commit per phase.

### FE-01 — Project bootstrap
- Create Ionic Angular app in `frontend/` from official sidemenu starter.
- Set title/branding to **WebSmartStorage**.
- Enable PWA support.

### FE-02 — JSON-driven left menu
- Create `src/assets/config/menu.json`.
- Render two-level menu only:
  - Level 1: accordion groups.
  - Level 2: route items.
- Wire router from config metadata.

### FE-03 — Theme management
- Implement light/dark theme service.
- Persist selected theme in LocalStorage.
- Apply a logo-inspired palette through Ionic variables with minimal overrides.

### FE-04 — Shared list pattern
- Build reusable list-page structure for search/filter/table behavior.
- Integrate existing `TableWrapper` concept for list rendering.

### FE-05 — ItemGroups first slice
- Build ItemGroups list page.
- Add row-click navigation to ItemGroup editor page/component.
- Keep authentication disabled for now.

### FE-06 — Quality and handoff
- Add component and routing/config tests.
- Run build/tests per phase.
- Perform manual UI checks.
- Update docs and changelog entries.

## Mandatory Gates (every phase)

1. Tests added/updated for changed components.
2. Build succeeds.
3. Manual behavior check completed.
4. Commit created for phase scope.

## Guardrails

- Do not implement OIDC/Keycloak login in this phase.
- Do not add third-level menus.
- Do not introduce heavy Ionic CSS rewrites.
- Do not add native OS packaging in this phase.

## Primary Artifacts

- Agent plan: `.github/agents/frontend-ionic-kickoff.agent.md`
- Frontend docs: `frontend/README.md`

## Change Tracking

When requirements shift, update:
1. The phase table in the agent file.
2. The changelog entry at the end of the agent file.

