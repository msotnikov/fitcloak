---
title: "Konfiguration"
nav_exclude: true
lang: de
permalink: /de/configuration.html
---

# Konfiguration

Alle verfügbaren Optionen finden Sie in [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json).

## Wichtige Einstellungen

| Feld | Beschreibung |
|------|-------------|
| `serverConfig.theme` | Pfad zum Verzeichnis Ihres Themes |
| `serverConfig.port` | Server-Port |
| `serverConfig.keycloakThemesPath` | Pfad zu den heruntergeladenen Keycloak-Basis-Themes |
| `serverConfig.devResourcesUrl` | URL des Vite/Webpack-Dev-Servers für HMR |
| `serverConfig.qaRealms` | Schnellzugriff-Links, die im Dashboard angezeigt werden |

## Mock-Daten

Daten, die Keycloak normalerweise an Templates übergibt, werden über JSON bereitgestellt:

- **Global**: `config.json` (Felder auf Root-Ebene wie `realm`, `url`, `locale`)
- **Pro Theme**: `<theme-dir>/mock-data.json` (überschreibt globale Daten)
- **Pro Anfrage**: URL-Query-Parameter (höchste Priorität)

Beispiel: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL-Routing

| URL-Muster | Theme-Typ |
|------------|-----------|
| `/*` (Standard) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

Die `.ftl`-Erweiterung ist optional: `/login` und `/login.ftl` funktionieren beide.
