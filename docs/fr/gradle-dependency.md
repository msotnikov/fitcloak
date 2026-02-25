---
title: "Dependance Gradle"
nav_exclude: true
lang: fr
permalink: /fr/gradle-dependency.html
---

# Utiliser Fitcloak comme dependance Gradle

Au lieu de cloner le depot Fitcloak, vous pouvez le connecter directement depuis votre propre projet Gradle en utilisant les [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). Gradle clonera et compilera Fitcloak automatiquement en arriere-plan.

C'est utile lorsque vous avez un **depot separe pour votre theme Keycloak** et que vous souhaitez lancer l'apercu Fitcloak sans le cloner manuellement.

## Prerequis

- **Java 17+**
- **Gradle 6.1+** (Gradle Wrapper recommande)

## Installation

### 1. Ajouter la source dependency dans `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Ajouter la dependance et la tache d'apercu dans `build.gradle`

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // version = tag git
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Lancer

```bash
./gradlew preview
```

Lors du premier lancement, Gradle clonera le depot Fitcloak et le compilera. Les lancements suivants utilisent le build en cache.

## Comment ca fonctionne

- La version dans la dependance (`0.1.0`) doit correspondre a un **tag git** dans le depot Fitcloak.
- Gradle clone le depot dans son cache interne (`~/.gradle/`), le compile et rend les classes disponibles dans votre classpath.
- La tache `preview` lance `fitcloak.PreviewServer` — le meme serveur qui s'execute via `./gradlew run` dans le depot Fitcloak lui-meme.

## Configuration

Le serveur d'apercu lit `config.json` depuis le **repertoire de travail** (la racine de votre projet). Creez-le de la meme maniere que decrit dans [Configuration](./configuration) :

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Vous aurez egalement besoin des themes de base Keycloak. Telechargez-les a l'aide du [script d'installation](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) ou copiez-les depuis une installation Fitcloak existante.
