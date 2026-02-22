---
title: Vite / HMR Integration
nav_order: 5
---

# Vite / HMR Integration

The included `demo/` directory is a working example of Vite integration with SCSS and React.

## How it works

1. Set `devResourcesUrl` in config: `"http://localhost:5173/"`
2. In `theme.properties`, reference source files directly: `styles=css/login.css src/theme.scss`
3. Fitcloak proxies `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` to the Vite dev server
4. Vite compiles SCSS, JSX, TypeScript (or other preprocessors) on the fly
5. Falls back to local files if the dev server is unavailable

## Using with your own theme

Set up a Vite project in your theme directory with SCSS/PostCSS/etc., reference source files in `theme.properties`, and point `devResourcesUrl` to Vite.

### Example: adding SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Create `vite.config.js`:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Reference SCSS in `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Example: adding React

The demo theme shows this pattern — see `demo/src/PasswordStrength.jsx` for a React component that enhances the login page. The key is using `<script type="module">` in your custom `.ftl` template instead of the standard `properties.scripts` mechanism.
