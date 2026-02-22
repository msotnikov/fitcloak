---
title: "कॉन्फ़िगरेशन"
nav_exclude: true
lang: hi
permalink: /hi/configuration/
---

# कॉन्फ़िगरेशन

सभी विकल्पों के लिए [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) देखें।

## मुख्य सेटिंग्स

| फ़ील्ड | विवरण |
|--------|--------|
| `serverConfig.theme` | आपकी थीम डायरेक्टरी का पथ |
| `serverConfig.port` | सर्वर पोर्ट |
| `serverConfig.keycloakThemesPath` | डाउनलोड की गई Keycloak बेस थीम का पथ |
| `serverConfig.devResourcesUrl` | HMR के लिए Vite/Webpack डेव सर्वर URL |
| `serverConfig.qaRealms` | डैशबोर्ड पर दिखाए जाने वाले त्वरित-पहुँच लिंक |

## मॉक डेटा

Keycloak सामान्यतः जो डेटा टेम्पलेट को पास करता है, वह JSON के माध्यम से प्रदान किया जाता है:

- **वैश्विक**: `config.json` (रूट-लेवल फ़ील्ड जैसे `realm`, `url`, `locale`)
- **प्रति-थीम**: `<theme-dir>/mock-data.json` (वैश्विक को ओवरराइड करता है)
- **प्रति-रिक्वेस्ट**: URL क्वेरी पैरामीटर (सर्वोच्च प्राथमिकता)

उदाहरण: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL रूटिंग

| URL पैटर्न | थीम प्रकार |
|------------|-----------|
| `/*` (डिफ़ॉल्ट) | लॉगिन |
| `/account/*` | अकाउंट |
| `/email/*` | ईमेल |
| `/admin/*` | एडमिन |

`.ftl` एक्सटेंशन वैकल्पिक है: `/login` और `/login.ftl` दोनों काम करते हैं।
