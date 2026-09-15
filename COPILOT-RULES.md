# Copilot Development Rules for WebSmartStorage

These rules apply to all Copilot-assisted work on this repository (backend, frontend, documentation).

## Git & Version Control

### ⚠️ CRITICAL: No Git Commands from Copilot

**Copilot must NEVER execute git commands.** The user will perform all git operations themselves.

**Copilot's role:**
- Provide clear, conventional commit messages when code changes are complete
- Suggest commit messages in a format the user can copy/paste directly
- Format: Multi-line commit messages with subject + body when appropriate
- Include Co-authored-by trailer (if user agrees)

**User's role:**
- Execute all `git add`, `git commit`, `git push` commands
- Review and customize commit messages if desired
- Decide when and what to commit

**Example (NO Co-authored-by trailer):**
```
feat(frontend): add json-driven two-level menu system

- Created MenuComponent as standalone component
- Added menu.json config with 3 groups
- Implemented MenuService using Signals
- Build verified: 499 KB main bundle
```

**IMPORTANT RESTRICTIONS:**
- ❌ **NEVER include `Co-authored-by: Copilot ...` trailer** — Commits authored solely by the user
- ❌ **NEVER invoke git commands** — User executes all git operations
- ✅ Provide commit message text only (format ready to copy/paste)

## Backend Development (Spring Boot / Maven)

- Use **hexagonal architecture** strictly (domain → data → infra → api-service)
- All components must follow the port/adapter pattern
- Use MapStruct for ALL object mappings (no hand-written converters)
- ReactiveForms for web forms (future Angular frontend)
- No NgModule for components; use standalone components
- Tests: `@DataJpaTest` for persistence, `@WebMvcTest` for controllers, `@SpringBootTest` for integration

## Frontend Development (Angular + Ionic)

- **Architecture:** Standalone components (Angular 22 modern pattern, `standalone: true`)
- **Forms:** ReactiveForms + Signals (Signals only for UI state: theme, loading, menu expanded)
- **Components:** Import dependencies directly in `imports` array (no NgModule declarations)
- **Ionic imports:** Import components from `@ionic/angular` (not `/standalone` path)
- **Animations:** Always include `provideAnimations()` in bootstrap providers
- **No template ngModel:** Use ReactiveForms for all editor/filter forms
- **Menu:** JSON-driven from `src/assets/config/menu.json`
- **Theming:** CSS custom properties only (no per-component styling)

## Documentation & Planning

- Use conventional commit format: `feat|fix|docs|refactor|test(scope): description`
- Keep one logical change per commit
- Update changelog entries in agent/skill files when goals change
- Document decisions in phase tracking tables (agent files)

## Dependency & Tool Management

- Backend: Maven only (no Gradle). Java 25. Spring Boot 4.1.1.
- Frontend: npm. Angular 22. Ionic 9. TypeScript.
- Multi-DB support (PostgreSQL, MySQL, SQLite) — validate new changes against all three.
- No new tooling added without explicit user request.

---

**Last updated:** 2026-09-15  
**Scope:** Entire repository (backend, frontend, documentation)
