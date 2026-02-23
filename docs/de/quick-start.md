---
title: "Schnellstart"
nav_exclude: true
lang: de
permalink: /de/quick-start.html
---

# Schnellstart

## 1. Klonen & konfigurieren

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak-Basis-Themes herunterladen

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Neueste Version (Main-Branch)
./setup-keycloak-themes.sh archive/release/23.0   # Oder eine bestimmte Version
```

Das Skript lädt Keycloaks FreeMarker-Templates und PatternFly-CSS-Assets vom selben Commit herunter, sodass die Versionen immer übereinstimmen.

## 3. Demo ausprobieren

Das Projekt enthält ein Demo-Theme mit Vite/SCSS-Integration und einem React-Passwortstärke-Widget:

```bash
# Demo-Abhängigkeiten installieren
cd demo && npm install && cd ..

# config.json bearbeiten und setzen:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Vite Dev-Server starten (in einem Terminal)
cd demo && npm run dev

# Fitcloak starten (in einem anderen Terminal)
./gradlew run
```

Öffnen Sie [http://localhost:3030](http://localhost:3030). Bearbeiten Sie `demo/src/theme.scss` und aktualisieren Sie die Seite -- Änderungen erscheinen sofort über Vite. Die Login-Seite enthält einen React-basierten Passwortstärke-Indikator, der zeigt, dass der Vite-Proxy von Fitcloak JSX/React zusätzlich zu SCSS verarbeitet.

## 4. Eigenes Theme verwenden

Setzen Sie `serverConfig.theme` in `config.json` auf das Verzeichnis Ihres Themes:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
