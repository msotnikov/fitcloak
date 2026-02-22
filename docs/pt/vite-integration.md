---
title: Integracao Vite / HMR
nav_exclude: true
lang: pt
permalink: /pt/vite-integration/
---

# Integracao Vite / HMR

O diretorio `demo/` incluido e um exemplo funcional de integracao Vite com SCSS e React.

## Como funciona

1. Defina `devResourcesUrl` na configuracao: `"http://localhost:5173/"`
2. Em `theme.properties`, referencie os arquivos fonte diretamente: `styles=css/login.css src/theme.scss`
3. O Fitcloak faz proxy de `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` para o servidor de desenvolvimento Vite
4. O Vite compila SCSS, JSX, TypeScript (ou outros pre-processadores) em tempo real
5. Se o servidor de desenvolvimento nao estiver disponivel, recorre aos arquivos locais

## Uso com seu proprio tema

Configure um projeto Vite no diretorio do seu tema com SCSS/PostCSS/etc., referencie os arquivos fonte em `theme.properties` e aponte `devResourcesUrl` para o Vite.

### Exemplo: adicionar SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Crie `vite.config.js`:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Referencie SCSS em `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Exemplo: adicionar React

O tema demo mostra esse padrao -- consulte `demo/src/PasswordStrength.jsx` para um componente React que aprimora a pagina de login. A chave e usar `<script type="module">` no seu template `.ftl` personalizado em vez do mecanismo padrao `properties.scripts`.
