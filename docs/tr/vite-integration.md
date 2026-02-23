---
title: "Vite / HMR Entegrasyonu"
nav_exclude: true
lang: tr
permalink: /tr/vite-integration.html
---

# Vite / HMR Entegrasyonu

Dahil edilen `demo/` dizini, SCSS ve React ile Vite entegrasyonunun çalışan bir örneğidir.

## Nasıl çalışır

1. Yapılandırmada `devResourcesUrl` ayarlayın: `"http://localhost:5173/"`
2. `theme.properties` dosyasında kaynak dosyalara doğrudan referans verin: `styles=css/login.css src/theme.scss`
3. Fitcloak `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` yollarını Vite geliştirme sunucusuna yönlendirir
4. Vite, SCSS, JSX, TypeScript (veya diğer ön işlemcileri) anında derler
5. Geliştirme sunucusu kullanılamazsa yerel dosyalara geri döner

## Kendi temanızla kullanma

Tema dizininizde SCSS/PostCSS/vb. ile bir Vite projesi oluşturun, `theme.properties` dosyasında kaynak dosyalara referans verin ve `devResourcesUrl`'yi Vite'a yönlendirin.

### Örnek: SCSS ekleme

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

`vite.config.js` oluşturun:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

`login/theme.properties` dosyasında SCSS'e referans verin:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Örnek: React ekleme

Demo tema bu deseni gösterir — giriş sayfasını geliştiren bir React bileşeni için `demo/src/PasswordStrength.jsx` dosyasına bakın. Önemli olan, standart `properties.scripts` mekanizması yerine özel `.ftl` şablonunuzda `<script type="module">` kullanmaktır.
