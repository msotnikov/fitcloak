---
title: "Integration Vite / HMR"
nav_exclude: true
lang: fr
permalink: /fr/vite-integration/
---

# Integration Vite / HMR

Le repertoire `demo/` inclus est un exemple fonctionnel d'integration Vite avec SCSS et React.

## Fonctionnement

1. Definissez `devResourcesUrl` dans la configuration : `"http://localhost:5173/"`
2. Dans `theme.properties`, referencez les fichiers sources directement : `styles=css/login.css src/theme.scss`
3. Fitcloak redirige `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` vers le serveur de developpement Vite
4. Vite compile SCSS, JSX, TypeScript (ou d'autres preprocesseurs) a la volee
5. Retombe sur les fichiers locaux si le serveur de developpement est indisponible

## Utilisation avec votre propre theme

Configurez un projet Vite dans le repertoire de votre theme avec SCSS/PostCSS/etc., referencez les fichiers sources dans `theme.properties`, et pointez `devResourcesUrl` vers Vite.

### Exemple : ajouter SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Creez `vite.config.js` :

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Referencez SCSS dans `login/theme.properties` :

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Exemple : ajouter React

Le theme de demonstration illustre ce pattern -- voir `demo/src/PasswordStrength.jsx` pour un composant React qui ameliore la page de connexion. La cle est d'utiliser `<script type="module">` dans votre template `.ftl` personnalise au lieu du mecanisme standard `properties.scripts`.
