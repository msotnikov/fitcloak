---
title: "Vite / HMR-Integration"
nav_exclude: true
lang: de
permalink: /de/vite-integration/
---

# Vite / HMR-Integration

Das enthaltene `demo/`-Verzeichnis ist ein funktionierendes Beispiel für die Vite-Integration mit SCSS und React.

## Funktionsweise

1. Setzen Sie `devResourcesUrl` in der Konfiguration: `"http://localhost:5173/"`
2. Referenzieren Sie in `theme.properties` Quelldateien direkt: `styles=css/login.css src/theme.scss`
3. Fitcloak leitet `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` an den Vite-Dev-Server weiter
4. Vite kompiliert SCSS, JSX, TypeScript (oder andere Präprozessoren) on-the-fly
5. Bei Nichtverfügbarkeit des Dev-Servers wird auf lokale Dateien zurückgegriffen

## Verwendung mit Ihrem eigenen Theme

Richten Sie ein Vite-Projekt in Ihrem Theme-Verzeichnis mit SCSS/PostCSS/etc. ein, referenzieren Sie Quelldateien in `theme.properties` und richten Sie `devResourcesUrl` auf Vite.

### Beispiel: SCSS hinzufügen

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Erstellen Sie `vite.config.js`:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Referenzieren Sie SCSS in `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Beispiel: React hinzufügen

Das Demo-Theme zeigt dieses Muster -- siehe `demo/src/PasswordStrength.jsx` für eine React-Komponente, die die Login-Seite erweitert. Der Schlüssel ist die Verwendung von `<script type="module">` in Ihrem benutzerdefinierten `.ftl`-Template anstelle des Standard-`properties.scripts`-Mechanismus.
