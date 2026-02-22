---
title: "Vite / HMR Entegrasyonu"
nav_exclude: true
lang: tr
permalink: /tr/vite-integration/
---

# Vite / HMR Entegrasyonu

Dahil edilen `demo/` dizini, SCSS ve React ile Vite entegrasyonunun calisan bir ornegidir.

## Nasil calisir

1. Yapilandirmada `devResourcesUrl` ayarlayin: `"http://localhost:5173/"`
2. `theme.properties` dosyasinda kaynak dosyalara dogrudan referans verin: `styles=css/login.css src/theme.scss`
3. Fitcloak `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` yollarini Vite gelistirme sunucusuna yonlendirir
4. Vite, SCSS, JSX, TypeScript (veya diger on islemcileri) aninda derler
5. Gelistirme sunucusu kullanilamazsa yerel dosyalara geri doner

## Kendi temanizla kullanma

Tema dizininizde SCSS/PostCSS/vb. ile bir Vite projesi olusturun, `theme.properties` dosyasinda kaynak dosyalara referans verin ve `devResourcesUrl`'yi Vite'a yonlendirin.

### Ornek: SCSS ekleme

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

`vite.config.js` olusturun:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

`login/theme.properties` dosyasinda SCSS'e referans verin:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### Ornek: React ekleme

Demo tema bu deseni gosterir — giris sayfasini gelistiren bir React bileseni icin `demo/src/PasswordStrength.jsx` dosyasina bakin. Onemli olan, standart `properties.scripts` mekanizmasi yerine ozel `.ftl` sablonunuzda `<script type="module">` kullanmaktir.
