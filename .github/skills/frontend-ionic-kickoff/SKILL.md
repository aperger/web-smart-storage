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
- Create Ionic Angular app in `frontend/` from official sidemenu starter:
  ```bash
  cd frontend
  ionic start smart-storage-frontend sidemenu --type=angular --skip-git
  ```
- Set title/branding to **WebSmartStorage**.
- Enable PWA support.

### FE-02 — JSON-driven left menu
- Create `src/assets/config/menu.json`.
- Render two-level menu only:
  - Level 1: accordion groups.
  - Level 2: route items.
- Wire router from config metadata.

### FE-03 — Theme management
- Implement light/dark theme service with Signal-based state.
- Persist selected theme in LocalStorage.
- Define CSS custom properties (--ion-color-*) for both themes:
  - **Light:** Blue primary (#2F71A2), Orange secondary (#FF9933), white background, black text.
  - **Dark:** Orange primary (#FF9933), Blue secondary (#2F71A2), dark background (#1A1A1A), light text (#E8E8E8).
- Organize SCSS in `src/assets/styles/variables/` and bundle in `theme.scss`.
- Apply theme via body class toggle (no per-component overrides needed).

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

- **Standalone Components:** All components must be standalone (`standalone: true`). Import Ionic components directly from `@ionic/angular`. No NgModule for component declarations.
- **Forms:** Use ReactiveForms for all editor forms (create/edit/search). Use Signals only for UI state (theme, menu toggle, loading flags). Avoid template-based ngModel for editors.
- Do not implement OIDC/Keycloak login in this phase.
- Do not add third-level menus.
- Do not introduce heavy Ionic CSS rewrites.
- Do not add native OS packaging in this phase.

## Primary Artifacts

- Agent plan: `.github/agents/frontend-ionic-kickoff.agent.md`
- Frontend docs: `frontend/README.md`
- Frontend theme strategy: `frontend/THEME-STRATEGY.md`
- Jest config: `frontend/jest.config.js`
- Jest setup: `frontend/setup-jest.ts`
- Playwright config: `frontend/playwright.config.ts`

## Testing Strategy

**Frameworks:** Jest + Playwright
- **Jest (unit):** faster, parallel execution, better modern Angular integration.
- **Playwright (e2e):** browser-level checks for menu visibility, navigation and responsive behavior.

**Test files:** Co-located with components as `*.spec.ts`.

**Running tests:**
```bash
npm test                          # Run all tests
npm test -- --coverage           # Run with coverage report
npm test -- --watch              # Watch mode
npm run test:e2e                 # Run Playwright e2e tests
npm run test:ci                  # Unit + e2e
```

**Mandatory gates per phase:**
1. Component unit tests written and passing
2. Service/integration tests for new features
3. Coverage >80% for new code
4. `npm run build` succeeds
5. `npm test` passes

**Component tests required for:**
- All new standalone components (MenuComponent, AppComponent, etc.)
- All services (MenuService, ThemeService, etc.)
- Router integration (verify routes wired correctly)
- Ionic component integration (split-pane, menu, headers, etc.)

## Change Tracking

When requirements shift, update:
1. The phase table in the agent file.
2. The changelog entry at the end of the agent file.
3. Commit all doc changes with a single conventional commit message (e.g., `docs(frontend): update project name and bootstrap command`).
