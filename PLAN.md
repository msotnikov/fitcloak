# Fitcloak Open-Source Readiness Plan

## Phase 1 — Critical (must-have before publishing)

| # | Task | Status |
|---|------|--------|
| 1.1 | Add MIT LICENSE file | DONE |
| 1.2 | Create `config.example.json` with generic data, add `config.json` to `.gitignore` | DONE |
| 1.3 | Remove hardcoded defaults — port, paths, URLs configurable via config/CLI | DONE |
| 1.4 | Add CLI argument support (`--port`, `--config`, `--theme`, `--help`, `--version`) | DONE |
| 1.5 | Add `.editorconfig` | DONE |

## Phase 2 — Code Quality

| # | Task | Status |
|---|------|--------|
| 2.1 | Replace `System.out.println` with SLF4J + simple logger | DONE |
| 2.2 | Add version to `build.gradle` (0.1.0) | DONE |
| 2.3 | Add input validation for query parameters (path traversal protection) | DONE |
| 2.4 | Fix unsafe casts, improve error handling (try-with-resources, @SuppressWarnings) | DONE |
| 2.5 | Parametrize `setup-keycloak-themes.sh` (--help, better UX, depth=1 clone) | DONE |

## Phase 3 — Documentation & Community

| # | Task | Status |
|---|------|--------|
| 3.1 | Rewrite README — clear positioning vs Keycloakify, better Quick Start, CLI docs | DONE |
| 3.2 | Add CONTRIBUTING.md | DONE |
| 3.3 | Add CHANGELOG.md | DONE |
| 3.4 | Remove KEYCLOAK_THEMES_DOC.md (copyright risk), link to original docs | DONE |
| 3.5 | Add demo theme that works out of the box | DONE |

## Phase 4 — CI/CD & Testing

| # | Task | Status |
|---|------|--------|
| 4.1 | Add JUnit 5 + test framework to build.gradle | DONE |
| 4.2 | Write tests for ThemeManager (8 tests) | DONE |
| 4.3 | Write tests for PreviewServer + DataModelProvider (12+4 tests) | DONE |
| 4.4 | Add GitHub Actions CI (build + test on Java 17/21) | DONE |

## Build Verification

- `./gradlew build` — **PASSED**
- `./gradlew test` — **ALL TESTS PASSED** (24 tests)
- Java package: `fitcloak.*`
- Java compatibility: 17+
