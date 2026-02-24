---
title: "त्वरित शुरुआत"
nav_exclude: true
lang: hi
permalink: /hi/quick-start.html
---

# त्वरित शुरुआत

## Fitcloak क्या है

Fitcloak Keycloak थीम विकसित करने के लिए एक **स्थानीय प्रीव्यू सर्वर** है। यह **Keycloak से अलग** चलता है और Keycloak को चलाने की आवश्यकता नहीं है।

Fitcloak क्या करता है:

- FreeMarker टेम्पलेट (`.ftl`) को Keycloak की उसी इनहेरिटेंस चेन का उपयोग करके असेंबल करता है (`Base → Parent → Child`)
- वास्तविक Keycloak डेटा (realm, user, client, आदि) के बजाय टेस्ट डेटा प्रतिस्थापित करता है
- `theme.properties` में निर्दिष्ट स्टाइल और स्क्रिप्ट लोड करता है
- परिणाम आपके ब्राउज़र में `http://localhost:3030` पर दिखाता है

जब थीम तैयार हो — आप इसे पैकेज करके वास्तविक Keycloak इंस्टेंस में इंस्टॉल करते हैं।

## आवश्यकताएँ

- **Java 17+** — एकमात्र आवश्यक डिपेंडेंसी। Gradle स्वचालित रूप से wrapper (`gradlew`) के माध्यम से डाउनलोड होता है।
- Node.js बेसिक थीम डेवलपमेंट के लिए **आवश्यक नहीं** है। यह केवल तभी चाहिए जब आप Vite, Webpack जैसे बंडलर उपयोग करना चाहें (देखें [Vite / HMR इंटीग्रेशन](./vite-integration))।

## चरण 1: क्लोन और कॉन्फ़िगर करें

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## चरण 2: Keycloak बेस थीम डाउनलोड करें

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # नवीनतम (main ब्रांच)
./setup-keycloak-themes.sh archive/release/23.0   # या कोई विशिष्ट संस्करण
```

स्क्रिप्ट Keycloak के FreeMarker टेम्पलेट और PatternFly CSS एसेट एक ही कमिट से डाउनलोड करती है, इसलिए संस्करण हमेशा मेल खाते हैं।

## चरण 3: चलाएँ

```bash
./gradlew run
```

[http://localhost:3030](http://localhost:3030) खोलें — आपको सभी उपलब्ध टेम्पलेट की सूची के साथ डैशबोर्ड दिखाई देगा। यही Fitcloak है — एक स्थानीय सर्वर जो आपकी थीम को बिना Keycloak के रेंडर करता है।

## चरण 4: अपनी खुद की थीम का उपयोग करें

`config.json` में `serverConfig.theme` को अपनी थीम डायरेक्टरी की ओर इंगित करें:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

आपकी थीम डायरेक्टरी को मानक Keycloak थीम संरचना का पालन करना चाहिए:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # लॉगिन पेज ओवरराइड
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

थीम संरचना और विकास के बारे में अधिक जानकारी के लिए, देखें [थीम विकास](./theme-development)।

## चरण 5: बंडलर से सीधे रिसोर्स कनेक्ट करें (वैकल्पिक)

config.json संपादित करें और सेट करें:
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# Vite डेव सर्वर शुरू करें (टर्मिनल 1)
npm run dev

# Fitcloak शुरू करें (टर्मिनल 2)
./gradlew run
```

## चरण 6: थीम को Keycloak में पैकेज और इंस्टॉल करें

जब थीम तैयार हो, इसे Keycloak में डिप्लॉय करें:

1. थीम डायरेक्टरी को `<KEYCLOAK_HOME>/themes/` में कॉपी करें:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Keycloak को रीस्टार्ट करें (या Docker उपयोग कर रहे हैं तो रीबिल्ड करें)।

3. Keycloak एडमिन कंसोल में, **Realm Settings → Themes** पर जाएँ और अपनी थीम चुनें।

यदि आपने Vite/Webpack का उपयोग किया है — पहले फ्रंटएंड रिसोर्स बिल्ड करें (`npm run build`) और सुनिश्चित करें कि कंपाइल की गई फ़ाइलें आपकी थीम की `resources/` डायरेक्टरी में हैं।

## डेमो थीम चलाना

प्रोजेक्ट में Vite/SCSS इंटीग्रेशन और React पासवर्ड स्ट्रेंथ विजेट के साथ एक डेमो थीम शामिल है। डेमो के लिए **Node.js आवश्यक** है क्योंकि यह Vite का उपयोग करता है।

```bash
# डेमो डिपेंडेंसी इंस्टॉल करें
cd demo && npm install && cd ..

# config.json संपादित करें:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Vite डेव सर्वर शुरू करें (टर्मिनल 1)
cd demo && npm run dev

# Fitcloak शुरू करें (टर्मिनल 2)
./gradlew run
```

[http://localhost:3030](http://localhost:3030) खोलें। `demo/src/theme.scss` संपादित करें — बदलाव Vite HMR के माध्यम से तुरंत दिखाई देते हैं।
