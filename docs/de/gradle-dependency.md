---
title: "Gradle-Abhangigkeit"
nav_exclude: true
lang: de
permalink: /de/gradle-dependency.html
---

# Fitcloak als Gradle-Abhangigkeit verwenden

Anstatt das Fitcloak-Repository zu klonen, konnen Sie es direkt aus Ihrem eigenen Gradle-Projekt uber [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html) einbinden. Gradle klont und baut Fitcloak automatisch im Hintergrund.

Dies ist nutzlich, wenn Sie ein **separates Repository fur Ihr Keycloak-Theme** haben und die Fitcloak-Vorschau ohne manuelles Klonen starten mochten.

## Voraussetzungen

- **Java 17+**
- **Gradle 6.1+** (Gradle Wrapper empfohlen)

## Einrichtung

### 1. Source Dependency in `settings.gradle` hinzufugen

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Abhangigkeit und Vorschau-Task in `build.gradle` hinzufugen

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // Version = Git-Tag
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Starten

```bash
./gradlew preview
```

Beim ersten Start klont Gradle das Fitcloak-Repository und baut es. Nachfolgende Starts verwenden den gecachten Build.

## Wie es funktioniert

- Die Version in der Abhangigkeit (`0.1.0`) muss mit einem **Git-Tag** im Fitcloak-Repository ubereinstimmen.
- Gradle klont das Repository in seinen internen Cache (`~/.gradle/`), baut es und stellt die Klassen in Ihrem Classpath bereit.
- Der `preview`-Task startet `fitcloak.PreviewServer` — denselben Server, der uber `./gradlew run` im Fitcloak-Repository selbst gestartet wird.

## Konfiguration

Der Vorschau-Server liest `config.json` aus dem **Arbeitsverzeichnis** (Ihr Projektstammverzeichnis). Erstellen Sie es wie im Abschnitt [Konfiguration](./configuration) beschrieben:

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Ausserdem benotigen Sie die Keycloak-Basis-Themes. Laden Sie diese mit dem [Setup-Skript](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) herunter oder kopieren Sie sie aus einer bestehenden Fitcloak-Installation.
