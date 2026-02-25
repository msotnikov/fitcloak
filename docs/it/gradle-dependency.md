---
title: Dipendenza Gradle
nav_exclude: true
lang: it
permalink: /it/gradle-dependency.html
---

# Usare Fitcloak come dipendenza Gradle

Invece di clonare il repository Fitcloak, puoi collegarlo direttamente dal tuo progetto Gradle usando le [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). Gradle clonera e compilera Fitcloak automaticamente in background.

Questo e utile quando hai un **repository separato per il tuo tema Keycloak** e vuoi avviare l'anteprima Fitcloak senza clonarlo manualmente.

## Requisiti

- **Java 17+**
- **Gradle 6.1+** (si raccomanda Gradle Wrapper)

## Configurazione

### 1. Aggiungere la source dependency in `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Aggiungere la dipendenza e il task di anteprima in `build.gradle`

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // versione = git tag
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Avviare

```bash
./gradlew preview
```

Al primo avvio Gradle clonera il repository Fitcloak e lo compilera. Gli avvii successivi utilizzano la build in cache.

## Come funziona

- La versione nella dipendenza (`0.1.0`) deve corrispondere a un **git tag** nel repository Fitcloak.
- Gradle clona il repository nella sua cache interna (`~/.gradle/`), lo compila e rende le classi disponibili nel tuo classpath.
- Il task `preview` avvia `fitcloak.PreviewServer` — lo stesso server che si avvia tramite `./gradlew run` nel repository Fitcloak stesso.

## Configurazione del server

Il server di anteprima legge `config.json` dalla **directory di lavoro** (la radice del tuo progetto). Crealo come descritto nella sezione [Configurazione](./configuration):

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Avrai anche bisogno dei temi base di Keycloak. Scaricali usando lo [script di setup](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) o copiali da un'installazione Fitcloak esistente.
