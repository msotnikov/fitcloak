---
title: "Vite / HMR इंटीग्रेशन"
nav_exclude: true
lang: hi
permalink: /hi/vite-integration/
---

# Vite / HMR इंटीग्रेशन

शामिल `demo/` डायरेक्टरी SCSS और React के साथ Vite इंटीग्रेशन का एक कार्यशील उदाहरण है।

## यह कैसे काम करता है

1. कॉन्फ़िग में `devResourcesUrl` सेट करें: `"http://localhost:5173/"`
2. `theme.properties` में, सोर्स फ़ाइलों को सीधे रेफ़रेंस करें: `styles=css/login.css src/theme.scss`
3. Fitcloak `/resources/*`, `/src/*`, `/@*`, `/node_modules/*` को Vite डेव सर्वर पर प्रॉक्सी करता है
4. Vite SCSS, JSX, TypeScript (या अन्य प्रीप्रोसेसर) को तुरंत कंपाइल करता है
5. डेव सर्वर अनुपलब्ध होने पर स्थानीय फ़ाइलों पर फ़ॉलबैक करता है

## अपनी थीम के साथ उपयोग करना

अपनी थीम डायरेक्टरी में SCSS/PostCSS/आदि के साथ एक Vite प्रोजेक्ट सेट करें, `theme.properties` में सोर्स फ़ाइलों को रेफ़रेंस करें, और `devResourcesUrl` को Vite की ओर इंगित करें।

### उदाहरण: SCSS जोड़ना

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

`vite.config.js` बनाएँ:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

`login/theme.properties` में SCSS रेफ़रेंस करें:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### उदाहरण: React जोड़ना

डेमो थीम इस पैटर्न को दर्शाती है — लॉगिन पेज को बेहतर बनाने वाले React कंपोनेंट के लिए `demo/src/PasswordStrength.jsx` देखें। कुंजी यह है कि अपने कस्टम `.ftl` टेम्पलेट में मानक `properties.scripts` मेकेनिज़्म के बजाय `<script type="module">` का उपयोग करें।
