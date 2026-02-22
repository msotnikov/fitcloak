---
title: "त्वरित शुरुआत"
nav_exclude: true
lang: hi
permalink: /hi/quick-start/
---

# त्वरित शुरुआत

## 1. क्लोन और कॉन्फ़िगर करें

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak बेस थीम डाउनलोड करें

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # नवीनतम (main ब्रांच)
./setup-keycloak-themes.sh archive/release/23.0   # या कोई विशिष्ट संस्करण
```

स्क्रिप्ट Keycloak के FreeMarker टेम्पलेट और PatternFly CSS एसेट एक ही कमिट से डाउनलोड करती है, इसलिए संस्करण हमेशा मेल खाते हैं।

## 3. डेमो आज़माएँ

प्रोजेक्ट में Vite/SCSS इंटीग्रेशन और React पासवर्ड स्ट्रेंथ विजेट के साथ एक डेमो थीम शामिल है:

```bash
# डेमो डिपेंडेंसी इंस्टॉल करें
cd demo && npm install && cd ..

# config.json संपादित करें और सेट करें:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Vite डेव सर्वर शुरू करें (एक टर्मिनल में)
cd demo && npm run dev

# Fitcloak शुरू करें (दूसरे टर्मिनल में)
./gradlew run
```

[http://localhost:3030](http://localhost:3030) खोलें। `demo/src/theme.scss` संपादित करें और रिफ्रेश करें — बदलाव Vite के माध्यम से तुरंत दिखाई देते हैं। लॉगिन पेज में React-पावर्ड पासवर्ड स्ट्रेंथ इंडिकेटर शामिल है, जो दर्शाता है कि Fitcloak का Vite प्रॉक्सी SCSS के अलावा JSX/React भी संभालता है।

## 4. अपनी खुद की थीम का उपयोग करें

`config.json` में `serverConfig.theme` को अपनी थीम डायरेक्टरी की ओर इंगित करें:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
