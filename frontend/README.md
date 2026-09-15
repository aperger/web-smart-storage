# WebSmartStorage Frontend

This directory will host the Ionic/Angular frontend for WebSmartStorage.

## Goals

- Build a responsive Angular + Ionic UI with PWA support.
- Support mobile-oriented interaction patterns.
- Keep styling close to Ionic defaults (light customization only).
- Reuse a table-wrapper-based list experience for QtSmartStorage-like search/filter/list workflows.

## Current Scope

- Ionic Angular project in `frontend/` (sidemenu layout baseline).
- **Architecture:** Standalone components (Angular 22 modern pattern).
- Two-level left hamburger menu with three groups:
  - **Base Data:** Countries, VAT Keys, Currencies, Item Groups, Item Types, Payment Methods, Partners, Master Items
  - **Documents:** Invoices, NAV Invoices
  - **System:** Profile, Settings, Logout
- Menu and route metadata loaded from JSON config.
- Light/dark theme switch persisted in LocalStorage.
- First feature slice: ItemGroups list view with row click navigation to editor view.

## Out of Scope (for now)

- OAuth2/OIDC login (Keycloak) implementation.
- Native OS packaging/deployment targets.
- Third-level menu nesting.

## Bootstrap Command

To start the frontend project:

```bash
cd frontend
ionic start smart-storage-frontend sidemenu --type=angular --skip-git
```

This creates the `smart-storage-frontend` Ionic Angular application with a sidemenu layout baseline.

**Note:** The project has been refactored to use **standalone components** (Angular 22 modern pattern). All components use `standalone: true` and import dependencies directly in their `imports` arrays.

## Planned Structure (Angular conventions)

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

## Development Expectations

- Keep each phase small and commit at phase end.
- Add or update component tests together with implementation.
- Run build and relevant tests before each phase commit.
- Perform manual UI verification for responsive/menu/theme/list-detail behavior.
- **Components:** All components are **standalone** (`standalone: true`). Import Ionic components directly from `@ionic/angular` in each component's `imports` array. Do NOT declare components in NgModule.
- **Forms:** Use ReactiveForms with `FormBuilder` and `FormGroup` for all editor/filter forms. Use Signals selectively for component UI state (theme, loading, menu expanded) where reactive side effects provide significant benefit.
- **Avoid:** Template-based ngModel for editors, NgModule-based component declarations, and pure-Signal-based forms (not yet stable enough).

## Color Palette for Themes

Primary colors extracted from https://pergersoft.hu/sites/default/files/pslogosmall.png:
- **Blue:** `#2F71A2` (professional, medium-dark)
- **Orange:** `#FF9933` (warm, vibrant)

### Implementation Approach

Use CSS custom properties (Ionic variables) to define both theme schemas without per-component customization:

**Light Theme:**
- Primary: Blue (#2F71A2) → buttons, links, highlights
- Secondary: Orange (#FF9933) → accents, badges
- Background: White (#FFFFFF)
- Text: Black (#000000)

**Dark Theme:**
- Primary: Orange (#FF9933) → buttons, links, highlights (high contrast on dark)
- Secondary: Blue (#2F71A2) → info, secondary actions
- Background: Dark (#1A1A1A)
- Text: Light (#E8E8E8)

All Ionic components automatically use these variables. Switch themes with a single body class toggle.

**See:** `frontend/THEME-STRATEGY.md` for complete implementation guide with code examples, file structure, and ThemeService implementation.

## Related Planning Files

- `.github/agents/frontend-ionic-kickoff.agent.md`
- `.github/skills/frontend-ionic-kickoff/SKILL.md`

## Future Repository Split

The frontend is intentionally started in this repository. It may be moved later to a separate dedicated frontend repository, but this is not part of the current phase.

