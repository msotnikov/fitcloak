---
title: Dependencia Gradle
nav_exclude: true
lang: es
permalink: /es/gradle-dependency.html
---

# Usar Fitcloak como dependencia Gradle

En lugar de clonar el repositorio de Fitcloak, puedes conectarlo directamente desde tu propio proyecto Gradle usando [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). Gradle clonara y compilara Fitcloak automaticamente en segundo plano.

Esto es util cuando tienes un **repositorio separado para tu tema de Keycloak** y quieres ejecutar la vista previa de Fitcloak sin clonarlo manualmente.

## Requisitos

- **Java 17+**
- **Gradle 6.1+** (se recomienda usar Gradle Wrapper)

## Configuracion

### 1. Agregar source dependency en `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Agregar dependencia y tarea de vista previa en `build.gradle`

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

### 3. Ejecutar

```bash
./gradlew preview
```

En la primera ejecucion, Gradle clonara el repositorio de Fitcloak y lo compilara. Las ejecuciones posteriores usan la compilacion en cache.

## Como funciona

- La version en la dependencia (`0.1.0`) debe coincidir con un **git tag** en el repositorio de Fitcloak.
- Gradle clona el repositorio en su cache interno (`~/.gradle/`), lo compila y hace las clases disponibles en tu classpath.
- La tarea `preview` ejecuta `fitcloak.PreviewServer` — el mismo servidor que se ejecuta con `./gradlew run` en el propio repositorio de Fitcloak.

## Configuracion del servidor

El servidor de vista previa lee `config.json` del **directorio de trabajo** (la raiz de tu proyecto). Crealo de la misma manera que se describe en [Configuracion](./configuration):

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Tambien necesitaras los temas base de Keycloak. Descargalos usando el [script de configuracion](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) o copialos desde una instalacion existente de Fitcloak.
