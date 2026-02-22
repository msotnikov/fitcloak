---
title: "Интеграция с Vite / HMR"
nav_exclude: true
lang: ru
permalink: /ru/vite-integration/
---

# Интеграция с Vite / HMR

Включённая директория `demo/` — это рабочий пример интеграции Vite с SCSS и React.

## Как это работает

1. Установите `devResourcesUrl` в конфигурации: `"http://localhost:5173/"`
2. В `theme.properties` ссылайтесь на исходные файлы напрямую: `styles=css/login.css src/theme.scss`
3. Fitcloak проксирует `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` на dev-сервер Vite
4. Vite компилирует SCSS, JSX, TypeScript (или другие препроцессоры) на лету
5. При недоступности dev-сервера используются локальные файлы

## Использование с вашей темой

Настройте Vite-проект в директории вашей темы с SCSS/PostCSS/и т.д., укажите исходные файлы в `theme.properties` и направьте `devResourcesUrl` на Vite.

### Пример: добавление SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

Создайте `vite.config.js`:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

Укажите SCSS в `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Пример: добавление React

Демо-тема показывает этот паттерн — см. `demo/src/PasswordStrength.jsx` для React-компонента, улучшающего страницу входа. Ключевой момент — использование `<script type="module">` в вашем кастомном `.ftl`-шаблоне вместо стандартного механизма `properties.scripts`.
