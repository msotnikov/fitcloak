# Fitcloak

A lightweight local preview server for developing **native Keycloak FreeMarker themes** — without Docker, database, or running a full Keycloak instance.

**[Documentation](https://fitcloak.org)** | **[GitHub](https://github.com/msotnikov/fitcloak)** | **[Support on Patreon](https://www.patreon.com/msotnikov/gift)**

## How it works

Fitcloak is a local server that **runs separately from Keycloak**. Only Java 17+ is required.

1. You create or edit a theme — standard `.ftl` templates, CSS, JS
2. Fitcloak assembles templates using the same inheritance chain as Keycloak (`Base → Parent → Child`)
3. It substitutes test data and serves the rendered page to your browser
4. When the theme is ready — copy it to Keycloak and select it in the admin console
5. Optionally, you can use bundlers (Vite, Webpack, etc.) for HMR for faster development with any web framework

## Why Fitcloak?

Customizing Keycloak's login/account/email pages normally means a painful feedback loop: rebuild the JAR, restart Keycloak, clear caches, refresh. Fitcloak eliminates all of that — just save your file and see the result.

**Take any Keycloak template, point Fitcloak at it, and start hacking.** The built-in dev server proxy means you can use any frontend toolchain — Vite, Webpack, Parcel — with any framework or preprocessor: React, Vue, Svelte, SCSS, Tailwind, whatever you prefer. FreeMarker renders the page structure, your tools handle the frontend, and HMR keeps the feedback loop instant.

This gives you the full flexibility of modern frontend development while staying within Keycloak's native theming system: no custom SPIs, no vendor lock-in — just standard `.ftl` templates that deploy to any Keycloak instance as-is.

## Features

- **Instant feedback** — edit `.ftl` / `.css` / `.properties`, refresh browser
- **Full inheritance** — emulates Keycloak's `Base -> Parent -> Child` theme chain
- **Vite/HMR integration** — proxy to a dev server for hot module replacement
- **Dynamic testing** — override any template variable via URL query parameters
- **Dashboard** — template browser with inheritance visualization and QA links
- **Zero infrastructure** — just Java and Gradle, nothing else

![Fitcloak Dashboard](docs/assets/images/screenshot-dashboard.png)
![Fitcloak Register Page](docs/assets/images/screenshot-register.png)

## Quick Start

### Requirements

- **Java 17+** — the only required dependency. Gradle is downloaded automatically via the wrapper (`gradlew`).
- Node.js is **not required** for basic theme development. It is only needed if you want to use bundlers like Vite, Webpack, etc.

### 1. Clone & configure

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

### 2. Download Keycloak base themes

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Latest (main branch)
./setup-keycloak-themes.sh archive/release/23.0   # Or a specific version
```

The script downloads Keycloak's FreeMarker templates and PatternFly CSS assets from the same commit, so versions always match.

### 3. Run

```bash
./gradlew run
```

Open [http://localhost:3030](http://localhost:3030) — you'll see the dashboard with a list of all available templates. This is Fitcloak — a local server that renders your themes without Keycloak.

### 4. Use your own theme

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
    login.ftl                 # Override login page
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

### 5. Connect resources from a bundler (optional)

Edit config.json and set:
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# Start Vite dev server (terminal 1)
npm run dev

# Start Fitcloak (terminal 2)
./gradlew run
```

### 6. Package and install theme into Keycloak

When the theme is ready, deploy it to Keycloak:

1. Copy the theme directory to `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Restart Keycloak (or rebuild if using Docker).

3. In Keycloak admin console, go to **Realm Settings → Themes** and select your theme.

If you used Vite/Webpack — build frontend resources first (`npm run build`) and make sure the compiled files are in the `resources/` directory of your theme.

### Running the demo theme

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

## CLI Options

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Flag | Short | Description | Default |
|------|-------|-------------|---------|
| `--port` | `-p` | Server port | `3030` (or from config) |
| `--config` | `-c` | Config file path | `config.json` |
| `--theme` | `-t` | Theme path (overrides config) | — |
| `--help` | `-h` | Show help | — |
| `--version` | `-v` | Show version | — |

## Configuration

See [`config.example.json`](config.example.json) for all options.

### Key settings

| Field | Description |
|-------|-------------|
| `serverConfig.theme` | Path to your theme directory |
| `serverConfig.port` | Server port |
| `serverConfig.keycloakThemesPath` | Path to downloaded Keycloak base themes |
| `serverConfig.devResourcesUrl` | Vite/Webpack dev server URL for HMR |
| `serverConfig.qaRealms` | Quick-access links shown on the dashboard |

### Mock data

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

## Vite / HMR Integration

The included `demo/` directory is a working example of Vite integration.

1. Set `devResourcesUrl` in config: `"http://localhost:5173/"`
2. In `theme.properties`, reference source files directly: `styles=css/login.css src/theme.scss`
3. Fitcloak proxies `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` to the Vite dev server
4. Vite compiles SCSS (or other preprocessors) on the fly
5. Falls back to local files if the dev server is unavailable

To use with your own theme: set up a Vite project in your theme directory with SCSS/PostCSS/etc., reference source files in `theme.properties`, and point `devResourcesUrl` to Vite.

## Template Helpers

Fitcloak provides mock implementations of Keycloak's FreeMarker objects:

| Helper | Behavior |
|--------|----------|
| `${msg("key")}` | Localized message from `.properties` files |
| `${kcSanitize(value)}` | Returns value as-is (mock) |
| `messagesPerField.existsError('field')` | Returns `false` |
| `messagesPerField.get('field')` | Returns `""` |
| `auth.showUsername()` | Returns `true` |
| `auth.showResetCredentials()` | Returns `true` |

## Troubleshooting

### "has evaluated to null or missing" errors

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

This means a FreeMarker template references a variable that doesn't exist in the mock data. In a real Keycloak instance these variables are populated by the server — in Fitcloak you provide them via JSON.

**How to fix:**

1. **Add the missing variable to mock data.** Open your theme's `mock-data.json` (or `config.json`) and add the missing field. For the error above:

   ```json
   {
     "realm": {
       "name": "my-realm",
       "password": true
     }
   }
   ```

   Common `realm` fields that Keycloak templates expect:

   | Field | Type | Typical value | Used by |
   |-------|------|---------------|---------|
   | `password` | boolean | `true` | `login.ftl` — controls whether the password form is shown |
   | `registrationAllowed` | boolean | `true` | `login.ftl` — "Register" link |
   | `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "Forgot password" link |
   | `rememberMe` | boolean | `true` | `login.ftl` — "Remember me" checkbox |
   | `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — username field label |
   | `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — username field label |
   | `internationalizationEnabled` | boolean | `false` | `template.ftl` — language selector |

2. **Override per-request via URL query parameters.** Useful for quick testing without editing files:

   ```
   http://localhost:3030/login?realm.password=true&realm.rememberMe=false
   ```

3. **Use FreeMarker defaults in your templates.** If you're writing custom `.ftl` files, use the `!` (default) operator to guard against missing values:

   ```ftl
   <#-- Instead of: -->
   <#if realm.password>

   <#-- Use a default value: -->
   <#if (realm.password)!true>
   ```

   The `!` operator provides a fallback when the value is missing. `(realm.password)!true` means "use `realm.password` if it exists, otherwise `true`".

### Finding which variables a template needs

Keycloak templates reference many variables (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). When you override or add a new `.ftl` file, you may need to provide additional mock values.

**Approach:** look at the `.ftl` file, find all `${...}` expressions and `<#if ...>` conditions, then make sure each referenced object exists in your mock data. The demo's [`mock-data.json`](demo/mock-data.json) is a good starting point to copy from.


## Keycloak Themes Reference

For comprehensive documentation on Keycloak's theming system, see the official guide:
[Keycloak Theme Development](https://www.keycloak.org/docs/latest/server_development/#_themes)

## Support

If you find Fitcloak useful, consider [supporting the project on Patreon](https://www.patreon.com/msotnikov/gift).

## Development

```bash
./gradlew build    # Build
./gradlew test     # Run tests
./gradlew run      # Start server
```

Requires Java 17+.

## License

[MIT](LICENSE)
