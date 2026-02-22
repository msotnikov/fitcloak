---
title: Configuration
nav_order: 3
---

# Configuration

See [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) for all options.

## Key settings

| Field | Description |
|-------|-------------|
| `serverConfig.theme` | Path to your theme directory |
| `serverConfig.port` | Server port |
| `serverConfig.keycloakThemesPath` | Path to downloaded Keycloak base themes |
| `serverConfig.devResourcesUrl` | Vite/Webpack dev server URL for HMR |
| `serverConfig.qaRealms` | Quick-access links shown on the dashboard |

## Mock data

Data that Keycloak normally passes to templates is provided via JSON:

- **Global**: `config.json` (root-level fields like `realm`, `url`, `locale`)
- **Per-theme**: `<theme-dir>/mock-data.json` (overrides global)
- **Per-request**: URL query parameters (highest priority)

Example: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL Routing

| URL pattern | Theme type |
|-------------|------------|
| `/*` (default) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

The `.ftl` extension is optional: `/login` and `/login.ftl` both work.
