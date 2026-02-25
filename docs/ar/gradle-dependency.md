---
title: "اعتماد Gradle"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/gradle-dependency.html
---

# استخدام Fitcloak كاعتماد Gradle

بدلا من استنساخ مستودع Fitcloak، يمكنك ربطه مباشرة من مشروع Gradle الخاص بك باستخدام [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). سيقوم Gradle باستنساخ وبناء Fitcloak تلقائيا في الخلفية.

هذا مفيد عندما يكون لديك **مستودع منفصل لقالب Keycloak** وتريد تشغيل معاينة Fitcloak دون الاستنساخ يدويا.

## المتطلبات

- **Java 17+**
- **Gradle 6.1+** (يوصى باستخدام Gradle Wrapper)

## الاعداد

### 1. اضافة source dependency في `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. اضافة الاعتماد ومهمة المعاينة في `build.gradle`

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // الاصدار = وسم git
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. التشغيل

```bash
./gradlew preview
```

عند التشغيل الاول، سيقوم Gradle باستنساخ مستودع Fitcloak وبنائه. تستخدم عمليات التشغيل اللاحقة البناء المخزن مؤقتا.

## كيف يعمل

- يجب ان يتطابق الاصدار في الاعتماد (`0.1.0`) مع **وسم git** في مستودع Fitcloak.
- يقوم Gradle باستنساخ المستودع في ذاكرته التخزينية الداخلية (`~/.gradle/`)، ويبنيه، ويجعل الفئات متاحة في classpath الخاص بك.
- مهمة `preview` تشغل `fitcloak.PreviewServer` — نفس الخادم الذي يعمل عبر `./gradlew run` في مستودع Fitcloak نفسه.

## التكوين

يقرا خادم المعاينة `config.json` من **دليل العمل** (جذر مشروعك). قم بانشائه كما هو موضح في قسم [التكوين](./configuration):

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

ستحتاج ايضا الى قوالب Keycloak الاساسية. قم بتنزيلها باستخدام [نص الاعداد](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) او انسخها من تثبيت Fitcloak موجود.
