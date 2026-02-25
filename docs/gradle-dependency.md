---
title: Gradle Dependency
nav_order: 9
---

# Using Fitcloak as a Gradle Dependency

Instead of cloning the Fitcloak repository, you can connect it directly from your own Gradle project using [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). Gradle will clone and build Fitcloak automatically behind the scenes.

This is useful when you have a **separate repository for your Keycloak theme** and want to run Fitcloak preview without manually cloning it.

## Requirements

- **Java 17+**
- **Gradle 6.1+** (with Gradle Wrapper recommended)

## Setup

### 1. Add source dependency in `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Add dependency and preview task in `build.gradle`

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // version = git tag
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Run

```bash
./gradlew preview
```

On the first run Gradle will clone the Fitcloak repository and build it. Subsequent runs use the cached build.

## How it works

- The version in the dependency (`0.1.0`) must match a **git tag** in the Fitcloak repository.
- Gradle clones the repository into its internal cache (`~/.gradle/`), builds it, and makes the resulting classes available in your classpath.
- The `preview` task launches `fitcloak.PreviewServer` — the same server that runs via `./gradlew run` in the Fitcloak repository itself.

## Configuration

The preview server reads `config.json` from the **working directory** (your project root). Create it the same way as described in [Configuration]({% link configuration.md %}):

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

You will also need Keycloak base themes. Download them using the [setup script](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) or copy from an existing Fitcloak installation.
