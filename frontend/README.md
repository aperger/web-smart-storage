# WebSmartStorage Frontend

This directory will host the Ionic/Angular frontend for WebSmartStorage.

## Goals

- Build a responsive Angular + Ionic UI with PWA support.
- Support mobile-oriented interaction patterns.
- Keep styling close to Ionic defaults (light customization only).
- Reuse a table-wrapper-based list experience for QtSmartStorage-like search/filter/list workflows.

## Current Scope

- Ionic Angular project in `frontend/` (sidemenu layout baseline).
- Two-level left hamburger menu:
  - Level 1: menu groups (accordion).
  - Level 2: menu items.
- Menu and route metadata loaded from JSON config.
- Light/dark theme switch persisted in LocalStorage.
- First feature slice: ItemGroups list view with row click navigation to editor view.

## Out of Scope (for now)

- OAuth2/OIDC login (Keycloak) implementation.
- Native OS packaging/deployment targets.
- Third-level menu nesting.

## Planned Structure (Angular conventions)

```text
frontend/
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
```

## Development Expectations

- Keep each phase small and commit at phase end.
- Add or update component tests together with implementation.
- Run build and relevant tests before each phase commit.
- Perform manual UI verification for responsive/menu/theme/list-detail behavior.

## Related Planning Files

- `.github/agents/frontend-ionic-kickoff.agent.md`
- `.github/skills/frontend-ionic-kickoff/SKILL.md`

## Future Repository Split

The frontend is intentionally started in this repository. It may be moved later to a separate dedicated frontend repository, but this is not part of the current phase.

