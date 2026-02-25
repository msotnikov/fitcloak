---
title: "Gradle निर्भरता"
nav_exclude: true
lang: hi
permalink: /hi/gradle-dependency.html
---

# Fitcloak को Gradle निर्भरता के रूप में उपयोग करना

Fitcloak रिपॉजिटरी को क्लोन करने के बजाय, आप [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html) का उपयोग करके इसे सीधे अपने Gradle प्रोजेक्ट से जोड़ सकते हैं। Gradle बैकग्राउंड में स्वचालित रूप से Fitcloak को क्लोन और बिल्ड करेगा।

यह तब उपयोगी है जब आपके पास **Keycloak थीम के लिए एक अलग रिपॉजिटरी** है और आप मैन्युअल क्लोनिंग के बिना Fitcloak प्रीव्यू चलाना चाहते हैं।

## आवश्यकताएँ

- **Java 17+**
- **Gradle 6.1+** (Gradle Wrapper अनुशंसित)

## सेटअप

### 1. `settings.gradle` में source dependency जोड़ें

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. `build.gradle` में निर्भरता और प्रीव्यू टास्क जोड़ें

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // संस्करण = git टैग
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. चलाएँ

```bash
./gradlew preview
```

पहली बार चलाने पर Gradle Fitcloak रिपॉजिटरी को क्लोन और बिल्ड करेगा। बाद के रन कैश्ड बिल्ड का उपयोग करते हैं।

## यह कैसे काम करता है

- निर्भरता में संस्करण (`0.1.0`) Fitcloak रिपॉजिटरी में एक **git टैग** से मेल खाना चाहिए।
- Gradle रिपॉजिटरी को अपने आंतरिक कैश (`~/.gradle/`) में क्लोन करता है, इसे बिल्ड करता है और क्लासेज़ को आपके classpath में उपलब्ध कराता है।
- `preview` टास्क `fitcloak.PreviewServer` को शुरू करता है — वही सर्वर जो Fitcloak रिपॉजिटरी में `./gradlew run` के माध्यम से चलता है।

## कॉन्फ़िगरेशन

प्रीव्यू सर्वर **कार्यशील डायरेक्टरी** (आपके प्रोजेक्ट रूट) से `config.json` पढ़ता है। इसे [कॉन्फ़िगरेशन](./configuration) सेक्शन में बताए अनुसार बनाएँ:

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

आपको Keycloak बेस थीम्स की भी आवश्यकता होगी। इन्हें [सेटअप स्क्रिप्ट](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) का उपयोग करके डाउनलोड करें या किसी मौजूदा Fitcloak इंस्टॉलेशन से कॉपी करें।
