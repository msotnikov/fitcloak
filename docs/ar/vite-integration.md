---
title: "تكامل Vite / HMR"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/vite-integration.html
---

# تكامل Vite / HMR

مجلد `demo/` المُضمّن هو مثال عملي على تكامل Vite مع SCSS و React.

## كيف يعمل

1. اضبط `devResourcesUrl` في الإعدادات: `"http://localhost:5173/"`
2. في `theme.properties`، أشِر إلى الملفات المصدرية مباشرة: `styles=css/login.css src/theme.scss`
3. يقوم Fitcloak بتوجيه `/resources/*`، `/src/*`، `/@*`، `/node_modules/*` إلى خادم تطوير Vite
4. يقوم Vite بتحويل SCSS و JSX و TypeScript (أو معالجات مسبقة أخرى) فوراً
5. يعود إلى الملفات المحلية إذا كان خادم التطوير غير متاح

## الاستخدام مع قالبك الخاص

أنشئ مشروع Vite في مجلد قالبك مع SCSS/PostCSS/إلخ.، أشِر إلى الملفات المصدرية في `theme.properties`، ووجّه `devResourcesUrl` إلى Vite.

### مثال: إضافة SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

أنشئ `vite.config.js`:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

أشِر إلى SCSS في `login/theme.properties`:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### مثال: إضافة React

يوضح القالب التوضيحي هذا النمط — انظر `demo/src/PasswordStrength.jsx` لمكوّن React يحسّن صفحة تسجيل الدخول. المفتاح هو استخدام `<script type="module">` في قالب `.ftl` المخصص بدلاً من آلية `properties.scripts` القياسية.
