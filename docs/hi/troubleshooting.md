---
title: "समस्या निवारण"
nav_exclude: true
lang: hi
permalink: /hi/troubleshooting.html
---

# समस्या निवारण

## "has evaluated to null or missing" त्रुटियाँ

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

इसका मतलब है कि एक FreeMarker टेम्पलेट ऐसे वेरिएबल को रेफ़रेंस कर रहा है जो मॉक डेटा में मौजूद नहीं है। वास्तविक Keycloak इंस्टेंस में ये वेरिएबल सर्वर द्वारा भरे जाते हैं — Fitcloak में आप इन्हें JSON के माध्यम से प्रदान करते हैं।

### समाधान 1: मॉक डेटा में लापता वेरिएबल जोड़ें

अपनी थीम का `mock-data.json` (या `config.json`) खोलें और लापता फ़ील्ड जोड़ें:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

सामान्य `realm` फ़ील्ड जो Keycloak टेम्पलेट अपेक्षा करते हैं:

| फ़ील्ड | प्रकार | सामान्य मान | उपयोग |
|--------|--------|------------|-------|
| `password` | boolean | `true` | `login.ftl` — पासवर्ड फ़ॉर्म दिखाया जाए या नहीं नियंत्रित करता है |
| `registrationAllowed` | boolean | `true` | `login.ftl` — "रजिस्टर" लिंक |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "पासवर्ड भूल गए" लिंक |
| `rememberMe` | boolean | `true` | `login.ftl` — "मुझे याद रखें" चेकबॉक्स |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — यूज़रनेम फ़ील्ड लेबल |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — यूज़रनेम फ़ील्ड लेबल |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — भाषा चयनकर्ता |

### समाधान 2: URL क्वेरी पैरामीटर के माध्यम से प्रति-रिक्वेस्ट ओवरराइड करें

फ़ाइलें संपादित किए बिना त्वरित परीक्षण के लिए उपयोगी:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### समाधान 3: अपने टेम्पलेट में FreeMarker डिफ़ॉल्ट का उपयोग करें

यदि आप कस्टम `.ftl` फ़ाइलें लिख रहे हैं, तो लापता मानों से बचाव के लिए `!` (डिफ़ॉल्ट) ऑपरेटर का उपयोग करें:

```ftl
<#-- इसके बजाय: -->
<#if realm.password>

<#-- डिफ़ॉल्ट मान का उपयोग करें: -->
<#if (realm.password)!true>
```

`!` ऑपरेटर मान गायब होने पर एक फ़ॉलबैक प्रदान करता है। `(realm.password)!true` का अर्थ है "यदि `realm.password` मौजूद है तो उसका उपयोग करें, अन्यथा `true`"।

## यह पता लगाना कि टेम्पलेट को किन वेरिएबल की आवश्यकता है

Keycloak टेम्पलेट कई वेरिएबल रेफ़रेंस करते हैं (`realm`, `url`, `auth`, `login`, `social`, `properties`, आदि)। जब आप कोई नई `.ftl` फ़ाइल ओवरराइड या जोड़ते हैं, तो आपको अतिरिक्त मॉक मान प्रदान करने की आवश्यकता हो सकती है।

**तरीका:** `.ftl` फ़ाइल देखें, सभी `${...}` एक्सप्रेशन और `<#if ...>` शर्तें खोजें, फिर सुनिश्चित करें कि प्रत्येक रेफ़रेंस किया गया ऑब्जेक्ट आपके मॉक डेटा में मौजूद है। डेमो का [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) कॉपी करने के लिए एक अच्छा शुरुआती बिंदु है।
