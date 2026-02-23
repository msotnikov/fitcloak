---
title: "Integration Vite / HMR"
nav_exclude: true
lang: fr
permalink: /fr/vite-integration.html
---

# Intégration Vite / HMR

Le répertoire `demo/` inclus est un exemple fonctionnel d'intégration Vite avec SCSS et React.

## Fonctionnement

1. Définissez `devResourcesUrl` dans la configuration : `"http://localhost:5173/"`
2. Dans `theme.properties`, référencez les fichiers sources directement : `styles=css/login.css src/theme.scss`
3. Fitcloak redirige `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` vers le serveur de développement Vite
4. Vite compile SCSS, JSX, TypeScript (ou d'autres préprocesseurs) à la volée
5. Retombe sur les fichiers locaux si le serveur de développement est indisponible

## Utilisation avec votre propre thème

Configurez un projet Vite dans le répertoire de votre thème avec SCSS/PostCSS/etc., référencez les fichiers sources dans `theme.properties`, et pointez `devResourcesUrl` vers Vite.

### Exemple : ajouter SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Créez `vite.config.js` :

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Référencez SCSS dans `login/theme.properties` :

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Exemple : ajouter React

Le thème de démonstration illustre ce pattern -- voir `demo/src/PasswordStrength.jsx` pour un composant React qui améliore la page de connexion. La clé est d'utiliser `<script type="module">` dans votre template `.ftl` personnalisé au lieu du mécanisme standard `properties.scripts`.
