---
title: Integracao Vite / HMR
nav_exclude: true
lang: pt
permalink: /pt/vite-integration.html
---

# Integração Vite / HMR

O diretório `demo/` incluído é um exemplo funcional de integração Vite com SCSS e React.

## Como funciona

1. Defina `devResourcesUrl` na configuração: `"http://localhost:5173/"`
2. Em `theme.properties`, referencie os arquivos fonte diretamente: `styles=css/login.css src/theme.scss`
3. O Fitcloak faz proxy de `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` para o servidor de desenvolvimento Vite
4. O Vite compila SCSS, JSX, TypeScript (ou outros pré-processadores) em tempo real
5. Se o servidor de desenvolvimento não estiver disponível, recorre aos arquivos locais

## Uso com seu próprio tema

Configure um projeto Vite no diretório do seu tema com SCSS/PostCSS/etc., referencie os arquivos fonte em `theme.properties` e aponte `devResourcesUrl` para o Vite.

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

O tema demo mostra esse padrão -- consulte `demo/src/PasswordStrength.jsx` para um componente React que aprimora a página de login. A chave é usar `<script type="module">` no seu template `.ftl` personalizado em vez do mecanismo padrão `properties.scripts`.
