---
name: frontend-ionic-kickoff
description: >-
  Implementation plan for introducing an Ionic/Angular PWA frontend under
  frontend/, with JSON-driven two-level menu, table-wrapper list/detail flow,
  and light/dark theming.
tools: ['read_file', 'list_dir', 'grep_search', 'run_in_terminal', 'get_errors', 'insert_edit_into_file', 'replace_string_in_file', 'create_file', 'apply_patch', 'get_terminal_output', 'open_file', 'ask_questions', 'file_search', 'validate_cves', 'run_subagent']
---
# WebSmartStorage Frontend (Ionic/Angular) Kickoff Plan

## Phase Tracking Table

| Phase ID | Scope | Status | Owner | Branch | Commit(s) | Tests/Build | Manual Check | Notes |
|---|---|---|---|---|---|---|---|---|
| FE-00 | Baseline decisions and scaffolding prerequisites | Planned | TBD | TBD | TBD | TBD | TBD | Confirm Ionic/Angular versions and workspace standards |
| FE-01 | Create Ionic project in `frontend/` with app title WebSmartStorage | ✅ Complete | @klm96551 | main | 5d1e42a | npm run build ✅ | ✅ App runs on localhost:4200 | Used official Ionic sidemenu starter, consolidated to frontend/ |
| FE-02 | JSON-driven configurable two-level left hamburger menu | ✅ Complete | @klm96551 | main | 6d30528 | npm run build ✅ | ✅ Menu loads, accordion groups expand | **Updated:** MenuComponent should use standalone components (Angular 22 modern pattern). Refactor in progress. |
| FE-03 | Light/dark theme switch with LocalStorage persistence | Planned | TBD | TBD | TBD | TBD | TBD | Keep Ionic default CSS largely intact |
| FE-04 | Shared list-page pattern using table wrapper integration approach | Planned | TBD | TBD | TBD | TBD | TBD | Search/filter/list interaction in QtSmartStorage style |
| FE-05 | First example vertical slice: ItemGroups list -> row click -> editor view | Planned | TBD | TBD | TBD | TBD | TBD | Parent/child example route and components |
| FE-06 | Stabilization: tests, documentation, quality gates | Planned | TBD | TBD | TBD | TBD | TBD | Per-phase commit + tests/build/manual validation |

## Goal

Create an Ionic/Angular PWA frontend in this repository under `frontend/`, ready to consume the existing backend API and support a QtSmartStorage-like menu and list/detail UX.

## Scope and Constraints

- In scope now:
  - PWA-capable Ionic/Angular web app.
  - Responsive/mobile-friendly UI.
  - Left-side hamburger menu with exactly two levels.
  - JSON-based menu/routing metadata.
  - Light/dark theme toggle persisted in browser LocalStorage.
  - First working feature slice for ItemGroups.
- Out of scope now:
  - OAuth2/OIDC (Keycloak) login implementation.
  - Native OS packaging (Capacitor native targets).
  - Third menu level (submenu).

## Technology Stack & Coding Conventions

### Component Architecture: Standalone Components (Modern Angular 22 + Ionic 9)
- **All components are standalone** (marked with `standalone: true` in the decorator).
- **No NgModule for components.** Import only what each component needs via the `imports` array.
- Ionic 9 provides standalone component imports: import `IonMenu`, `IonItem`, `IonAccordion`, etc. directly from `@ionic/angular`.
- Example: `@Component({ selector: 'app-menu', standalone: true, imports: [IonMenu, IonItem, CommonModule, ...] })`
- Rationale: Standalone components are the modern pattern in Angular 22+; they reduce boilerplate, improve tree-shaking, and align with Ionic 9's component-first design.

### Forms: ReactiveForms + Signals
- **All editor forms** (create/edit/search filters) use `ReactiveForms` with `FormBuilder` and `FormGroup`.
- Use **Signals** for component-level UI state only (theme, menu expanded/collapsed, loading flags, modals).
- Rationale: ReactiveForms is mature, handles complex validation/async validators, and is battle-tested. Signals are adopted selectively for non-form state where reactive side effects have significant benefit.
- Do not use template-based `ngModel` for editors (hard to test, limited validation).
- Bridge forms and signals with `toSignal(form.valueChanges)` only when needed for reactive side effects.

## Implementation Steps

### FE-00 — Baseline and conventions

1. Confirm frontend root as `frontend/` in this repo.
2. Keep Angular-standard folder layout (`src/app`, `shared`, `core`, `features`, `assets`).
3. Decide route naming conventions and JSON schema for menu config.
4. Define commit strategy: one commit per phase.

### FE-01 — Bootstrap Ionic project

1. Create project in `frontend/` using official Ionic Angular sidemenu starter template:
   ```bash
   cd frontend
   ionic start smart-storage-frontend sidemenu --type=angular --skip-git
   ```
2. Move generated contents to `frontend/` root (or adjust paths accordingly).
3. Set application title/branding to **WebSmartStorage** in `angular.json` and `index.html`.
4. Enable PWA support (Angular service worker) and verify production build artifacts.
5. Keep framework defaults; avoid heavy global CSS overrides.

### FE-02 — Configurable two-level menu from JSON

1. Add menu configuration JSON under `frontend/src/assets/config/menu.json`.
2. Define model types (`MenuGroup`, `MenuItem`) in app code.
3. Build left-side menu component as **standalone**:
   - Import `IonMenu`, `IonHeader`, `IonItem`, `IonAccordion`, `IonAccordionGroup`, `IonList`, `IonLabel`, `IonIcon`, `CommonModule` directly in component `imports` array.
   - Do NOT declare in NgModule.
4. Render menu as:
   - Level 1: accordion groups.
   - Level 2: navigable items inside each group.
5. Wire router so each menu item maps to route details from config.
6. Enforce no third-level nesting in the schema.

### FE-03 — Theming (light/dark) with LocalStorage

1. Create a theme service handling:
   - current theme state (via Signal),
   - apply/remove theme classes,
   - persist selection to LocalStorage,
   - initialize on startup.
2. Define logo-inspired token palette for both themes using CSS custom properties:
   - **Light theme:** Blue primary (#2F71A2), Orange secondary (#FF9933)
   - **Dark theme:** Orange primary (#FF9933), Blue secondary (#2F71A2)
3. Organize SCSS in `src/assets/styles/variables/{colors-light,colors-dark}.scss` and bundle in `src/assets/styles/theme.scss`.
4. Apply theme via body class (`theme-light` / `theme-dark`).
5. Add UI toggle (header or side menu settings area).
6. **No per-component customization:** all Ionic components inherit theme colors automatically via CSS variables.

See `frontend/THEME-STRATEGY.md` for complete implementation guide with code examples.

### FE-04 — Reusable list-view pattern with table wrapper

1. Introduce a shared list container pattern compatible with the existing TableWrapper concept.
2. Copy the existing TableWrapper component into this project when FE-04 starts. Current known source path:
   - `/Users/klm96551/Sources/DIL/empl360-dil-fe/src/app/shared/components/table-view`
3. If another colleague provides the component from a different path, use that source instead; the key assumption is that an existing TableWrapper implementation is available and should be reused.
4. Define reusable list-page inputs:
   - columns,
   - backend query/filter bindings,
   - row-click navigation target.
5. Implement search/filter controls aligned with QtSmartStorage list behavior.
6. Ensure list pages remain feature-specific while table plumbing stays shared.

### FE-05 — First feature slice: ItemGroups

1. Add feature route/module for ItemGroups.
2. Create ItemGroups list page using the shared table wrapper pattern.
3. Wire row click to ItemGroup editor route/component.
4. Add initial editor component (frame/page) for create/edit flow structure.

### FE-06 — Validation, tests, and handoff readiness

1. Add or extend tests per phase:
   - component tests for menu rendering and theme toggle,
   - route/config tests for JSON menu mapping,
   - ItemGroups list/editor interaction tests.
2. Run build/tests at each phase boundary.
3. Manual verification checklist:
   - mobile viewport menu behavior,
   - dark/light persistence,
   - PWA installability and offline shell behavior,
   - list row navigation to editor.
4. Update docs and handoff notes.

## Mandatory End-of-Phase Gate

At the end of each phase:

1. Code complete for phase scope.
2. Related component tests added/updated.
3. Build and relevant test commands pass.
4. Manual UI checks complete.
5. Commit created with concise conventional message.

Suggested commit messages:
- `feat(frontend): bootstrap ionic pwa shell`
- `feat(frontend): add json-driven two-level menu`
- `feat(frontend): add theme toggle with local storage`
- `feat(frontend): add itemgroups list-editor first slice`
- `test(frontend): add menu and theme component tests`

## Documentation Update Workflow

When updating planning/strategy docs (agent, skill, README, THEME-STRATEGY):
1. Make doc changes with updated changelog entry.
2. Create a single commit with concise conventional message:
   - `docs(frontend): <what changed>`
   - Example: `docs(frontend): update bootstrap command and planned structure`
3. One commit per logical documentation change — do not batch unrelated updates.

## Suggested Frontend Structure

```text
frontend/
  angular.json
  package.json
  ionic.config.json
  src/
    app/
      core/
        config/
        layout/
        theme/
      shared/
        components/
          table-view/
      features/
        item-groups/
          pages/
          components/
          services/
          models/
    assets/
      config/
        menu.json
      styles/
        variables/
          colors-light.scss
          colors-dark.scss
        theme.scss
```

**Project name:** `smart-storage-frontend` (generated by `ionic start smart-storage-frontend sidemenu --type=angular`)

## Future Repository Split Note

Frontend starts in this repository for now. It may be moved later into a separate dedicated frontend repository, but that is not part of this phase.

## Theme Color Palette (from logo analysis)

Extracted from https://pergersoft.hu/sites/default/files/pslogosmall.png:
- **Primary Blue:** `#2F71A2` (professional, medium-dark)
- **Primary Orange:** `#FF9933` (warm, vibrant)

### Light Theme Color Schema
```
--ion-color-primary:     #2F71A2 (Blue)
--ion-color-secondary:   #FF9933 (Orange)
--ion-background-color:  #FFFFFF
--ion-text-color:        #000000
```

### Dark Theme Color Schema
```
--ion-color-primary:     #FF9933 (Orange)
--ion-color-secondary:   #2F71A2 (Blue)
--ion-background-color:  #1A1A1A
--ion-text-color:        #E8E8E8
```

Complete implementation guide with code examples: `frontend/THEME-STRATEGY.md`

## TableWrapper Source Note

- Reuse/copy the existing TableWrapper component when list pages are implemented (FE-04/FE-05).
- Current known source: `/Users/klm96551/Sources/DIL/empl360-dil-fe/src/app/shared/components/table-view`.
- This source path can differ by contributor machine; if different, use the collaborator-provided path and keep the component contract stable.

## Changelog

| Date | Author | Change |
|---|---|---|
| 2026-09-15 | @Atilla-Perger_afklm + Copilot planning session | Initial Ionic/Angular frontend kickoff plan with phase table, delivery gates, and handoff notes. |
| 2026-09-15 | @Atilla-Perger_afklm + Copilot planning session | Added explicit TableWrapper reuse instruction, known source path, and note that source path may vary by colleague. |
| 2026-09-15 | @Atilla-Perger_afklm + Copilot planning session | Added ReactiveForms + Signals technology guidelines: use ReactiveForms for all editor forms, Signals for UI state only. |
| 2026-09-15 | @Atilla-Perger_afklm + Copilot planning session | Analyzed logo and extracted primary colors: Blue #2F71A2, Orange #FF9933. Updated FE-03 theming strategy with palette guidance. |
| 2026-09-15 | @Atilla-Perger_afklm + Copilot planning session | Generated complete light/dark color schemas with CSS variables. Created `frontend/THEME-STRATEGY.md` implementation guide with ThemeService, SCSS structure, and zero-customization approach. |
| 2026-09-15 | @Atilla-Perger_afklm + Copilot planning session | Updated project name to `smart-storage-frontend` (from web-smart-storage). Updated FE-01 bootstrap command and directory structure. |
| 2026-09-15 | @klm96551 + Copilot | **FE-02 COMPLETE**: JSON-driven two-level menu system implemented. MenuComponent with NgModule architecture, MenuService using Signals, menu.json config with 3 groups. Build verified. Commit: 6d30528. Ready for FE-03 (theme system). |