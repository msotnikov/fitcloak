# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.1.0] - 2026-02-22

### Added
- MIT License
- CLI arguments support: `--port`, `--config`, `--theme`, `--help`, `--version`
- `config.example.json` template for new users
- `.editorconfig` for consistent code style across editors
- `CONTRIBUTING.md` with development guidelines
- `CHANGELOG.md`
- Demo theme (`demo/`) that works out of the box
- JUnit 5 test suite for ThemeManager, PreviewServer, DataModelProvider
- GitHub Actions CI pipeline (build + test on Java 17/21)
- SLF4J logging (replaced all `System.out.println`)
- Version info in `build.gradle` (0.1.0)
- Java package structure (`fitcloak.*`)
- Path traversal protection for query parameters
- Configurable port via config or CLI

### Changed
- Moved source files to `fitcloak` package
- `config.json` is now gitignored (user-specific)
- Replaced hardcoded port `3030` with configurable value
- Replaced hardcoded Keycloak themes path with config value
- Improved error messages (e.g., "copy config.example.json" hint)
- Better User-Agent for dev server proxy
- Consistent use of try-with-resources for output streams
- `setup-keycloak-themes.sh` now defaults to `base` directory name

### Removed
- `KEYCLOAK_THEMES_DOC.md` (copyright risk, linked to original docs instead)
- Old unpackaged Java source files (replaced by `fitcloak.*` package)

### Fixed
- Output streams not always closed properly (now using try-with-resources)
- Potential NPE in ThemeManager when keycloakThemes is null
