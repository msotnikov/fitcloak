---
title: "थीम विकास"
nav_exclude: true
lang: hi
permalink: /hi/theme-development.html
---

# थीम विकास

## थीम संरचना

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # लॉगिन पेज ओवरराइड करें
    register.ftl              # रजिस्ट्रेशन ओवरराइड करें
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # थीम-विशिष्ट टेस्ट डेटा
```

## टेम्पलेट हेल्पर्स

Fitcloak Keycloak के FreeMarker ऑब्जेक्ट्स के मॉक इम्प्लीमेंटेशन प्रदान करता है:

| हेल्पर | व्यवहार |
|--------|---------|
| `${msg("key")}` | `.properties` फ़ाइलों से स्थानीयकृत संदेश |
| `${advancedMsg("key")}` | `msg` के समान — कुंजी को फ़ॉलबैक के रूप में उपयोग करते हुए संदेश लुकअप |
| `${kcSanitize(value)}` | मान को जैसा-है लौटाता है (मॉक) |
| `messagesPerField.existsError('field')` | `false` लौटाता है |
| `messagesPerField.get('field')` | `""` लौटाता है |
| `auth.showUsername()` | `true` लौटाता है |
| `auth.showResetCredentials()` | `true` लौटाता है |

## Keycloak थीम संदर्भ

Keycloak की थीमिंग सिस्टम के व्यापक दस्तावेज़ीकरण के लिए, आधिकारिक गाइड देखें:
[Keycloak थीम विकास](https://www.keycloak.org/docs/latest/server_development/#_themes)
