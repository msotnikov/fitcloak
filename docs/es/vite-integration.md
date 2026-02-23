---
title: Integracion Vite / HMR
nav_exclude: true
lang: es
permalink: /es/vite-integration.html
---

# Integración Vite / HMR

El directorio `demo/` incluido es un ejemplo funcional de integración con Vite usando SCSS y React.

## Cómo funciona

1. Establece `devResourcesUrl` en la configuración: `"http://localhost:5173/"`
2. En `theme.properties`, referencia los archivos fuente directamente: `styles=css/login.css src/theme.scss`
3. Fitcloak redirige `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` al servidor de desarrollo Vite
4. Vite compila SCSS, JSX, TypeScript (u otros preprocesadores) en tiempo real
5. Si el servidor de desarrollo no está disponible, recurre a archivos locales

## Uso con tu propio tema

Configura un proyecto Vite en el directorio de tu tema con SCSS/PostCSS/etc., referencia los archivos fuente en `theme.properties` y apunta `devResourcesUrl` a Vite.

### Ejemplo: agregar SCSS

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

Referencia SCSS en `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Ejemplo: agregar React

El tema de demostración muestra este patrón — consulta `demo/src/PasswordStrength.jsx` para un componente React que mejora la página de inicio de sesión. La clave es usar `<script type="module">` en tu plantilla `.ftl` personalizada en lugar del mecanismo estándar `properties.scripts`.
