---
title: Integrazione Vite / HMR
nav_exclude: true
lang: it
permalink: /it/vite-integration/
---

# Integrazione Vite / HMR

La directory `demo/` inclusa è un esempio funzionante di integrazione Vite con SCSS e React.

## Come funziona

1. Imposta `devResourcesUrl` nella configurazione: `"http://localhost:5173/"`
2. In `theme.properties`, referenzia i file sorgente direttamente: `styles=css/login.css src/theme.scss`
3. Fitcloak fa da proxy per `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` verso il server di sviluppo Vite
4. Vite compila SCSS, JSX, TypeScript (o altri preprocessori) al volo
5. Se il server di sviluppo non è disponibile, ricorre ai file locali

## Utilizzo con il tuo tema

Configura un progetto Vite nella directory del tuo tema con SCSS/PostCSS/etc., referenzia i file sorgente in `theme.properties` e punta `devResourcesUrl` a Vite.

### Esempio: aggiungere SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Crea `vite.config.js`:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Referenzia SCSS in `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Esempio: aggiungere React

Il tema demo mostra questo pattern -- consulta `demo/src/PasswordStrength.jsx` per un componente React che migliora la pagina di login. La chiave è usare `<script type="module">` nel tuo template `.ftl` personalizzato invece del meccanismo standard `properties.scripts`.
