---
title: "Gradle Bagimliligi"
nav_exclude: true
lang: tr
permalink: /tr/gradle-dependency.html
---

# Fitcloak'i Gradle Bagimliligi Olarak Kullanma

Fitcloak deposunu klonlamak yerine, [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html) kullanarak kendi Gradle projenizden dogrudan baglayabilirsiniz. Gradle, Fitcloak'i arka planda otomatik olarak klonlayip derleyecektir.

Bu, **Keycloak temaniz icin ayri bir deponuz** oldugunda ve Fitcloak onizlemesini manuel klonlama yapmadan calistirmak istediginizde kullanislidir.

## Gereksinimler

- **Java 17+**
- **Gradle 6.1+** (Gradle Wrapper onerilir)

## Kurulum

### 1. `settings.gradle` dosyasina source dependency ekleyin

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. `build.gradle` dosyasina bagimlilik ve onizleme gorevi ekleyin

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // surum = git etiketi
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Calistirma

```bash
./gradlew preview
```

Ilk calistirmada Gradle, Fitcloak deposunu klonlayip derleyecektir. Sonraki calistirmalar onbellekteki derlemeyi kullanir.

## Nasil calisir

- Bagimliliktaki surum (`0.1.0`), Fitcloak deposundaki bir **git etiketi** ile eslesmelidir.
- Gradle, depoyu dahili onbellegine (`~/.gradle/`) klonlar, derler ve siniflari classpath'inizde kullanilabilir hale getirir.
- `preview` gorevi `fitcloak.PreviewServer`'i baslatir — Fitcloak deposunun kendisinde `./gradlew run` ile calistirilan ayni sunucu.

## Yapilandirma

Onizleme sunucusu `config.json` dosyasini **calisma dizininden** (proje kokunuz) okur. [Yapilandirma](./configuration) bolumunde anlatildigi gibi olusturun:

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Ayrica Keycloak temel temalarina ihtiyaciniz olacaktir. Bunlari [kurulum betigini](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) kullanarak indirin veya mevcut bir Fitcloak kurulumundan kopyalayin.
