---
title: Quick Start
nav_order: 2
---

# Quick Start

## 1. Clone & configure

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Download Keycloak base themes

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Latest (main branch)
./setup-keycloak-themes.sh archive/release/23.0   # Or a specific version
```

The script downloads Keycloak's FreeMarker templates and PatternFly CSS assets from the same commit, so versions always match.

## 3. Try the demo

The project includes a demo theme with Vite/SCSS integration and a React password strength widget:

```bash
# Install demo dependencies
cd demo && npm install && cd ..

# Edit config.json and set:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Start Vite dev server (in one terminal)
cd demo && npm run dev

# Start Fitcloak (in another terminal)
./gradlew run
```

Open [http://localhost:3030](http://localhost:3030). Edit `demo/src/theme.scss` and refresh — changes appear instantly via Vite. The login page includes a React-powered password strength indicator to demonstrate that Fitcloak's Vite proxy handles JSX/React in addition to SCSS.

## 4. Use your own theme

Point `serverConfig.theme` in `config.json` to your theme directory:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
