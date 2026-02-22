---
title: Integracion Vite / HMR
nav_exclude: true
lang: es
permalink: /es/vite-integration/
---

# Integracion Vite / HMR

El directorio `demo/` incluido es un ejemplo funcional de integracion con Vite usando SCSS y React.

## Como funciona

1. Establece `devResourcesUrl` en la configuracion: `"http://localhost:5173/"`
2. En `theme.properties`, referencia los archivos fuente directamente: `styles=css/login.css src/theme.scss`
3. Fitcloak redirige `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` al servidor de desarrollo Vite
4. Vite compila SCSS, JSX, TypeScript (u otros preprocesadores) en tiempo real
5. Si el servidor de desarrollo no esta disponible, recurre a archivos locales

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

El tema de demostracion muestra este patron -- consulta `demo/src/PasswordStrength.jsx` para un componente React que mejora la pagina de inicio de sesion. La clave es usar `<script type="module">` en tu plantilla `.ftl` personalizada en lugar del mecanismo estandar `properties.scripts`.
