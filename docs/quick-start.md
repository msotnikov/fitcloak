---
title: Quick Start
nav_order: 2
---

# Quick Start

## What is Fitcloak

Fitcloak is a **local preview server** for developing Keycloak themes. It runs **separately from Keycloak** and does not require running Keycloak itself.

What Fitcloak does:

- Assembles FreeMarker templates (`.ftl`) using the same inheritance chain as Keycloak (`Base → Parent → Child`)
- Substitutes test data instead of real Keycloak data (realm, user, client, etc.)
- Loads styles and scripts specified in `theme.properties`
- Serves the result to your browser at `http://localhost:3030`

When the theme is ready — you package it and install it into a real Keycloak instance.

## Requirements

- **Java 17+** — the only required dependency. Gradle is downloaded automatically via the wrapper (`gradlew`).
- Node.js is **not required** for basic theme development. It is only needed if you want to use bundlers like Vite, Webpack, etc. (see [Vite / HMR Integration]({% link vite-integration.md %})).

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

## 3. Run

```bash
./gradlew run
```

Open [http://localhost:3030](http://localhost:3030) — you'll see the dashboard with a list of all available templates. This is Fitcloak — a local server that renders your themes without Keycloak.

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

Your theme directory should follow the standard Keycloak theme structure:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Login page override
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

For more details on theme structure and development, see [Theme Development]({% link theme-development.md %}).

## 5. Connect resources directly from a bundler (optional)

Edit config.json and set:
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# Start Vite dev server (terminal 1)
npm run dev

# Start Fitcloak (terminal 2)
./gradlew run
```

## 6. Package and install theme into Keycloak

When the theme is ready, deploy it to Keycloak:

1. Copy the theme directory to `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Restart Keycloak (or rebuild if using Docker).

3. In Keycloak admin console, go to **Realm Settings → Themes** and select your theme.

If you used Vite/Webpack — build frontend resources first (`npm run build`) and make sure the compiled files are in the `resources/` directory of your theme.

## Running the demo theme

The project includes a demo theme with Vite/SCSS integration and a React password strength widget. The demo **requires Node.js** since it uses Vite.

```bash
# Install demo dependencies
cd demo && npm install && cd ..

# Edit config.json:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Start Vite dev server (terminal 1)
cd demo && npm run dev

# Start Fitcloak (terminal 2)
./gradlew run
```

Open [http://localhost:3030](http://localhost:3030). Edit `demo/src/theme.scss` — changes appear instantly via Vite HMR.
