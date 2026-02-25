---
title: Dependencia Gradle
nav_exclude: true
lang: pt
permalink: /pt/gradle-dependency.html
---

# Usar Fitcloak como dependencia Gradle

Em vez de clonar o repositorio do Fitcloak, voce pode conecta-lo diretamente do seu proprio projeto Gradle usando [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). O Gradle clonara e compilara o Fitcloak automaticamente em segundo plano.

Isso e util quando voce tem um **repositorio separado para o seu tema Keycloak** e quer executar a pre-visualizacao do Fitcloak sem clona-lo manualmente.

## Requisitos

- **Java 17+**
- **Gradle 6.1+** (recomenda-se usar o Gradle Wrapper)

## Configuracao

### 1. Adicionar source dependency no `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Adicionar dependencia e tarefa de pre-visualizacao no `build.gradle`

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // versao = git tag
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Executar

```bash
./gradlew preview
```

Na primeira execucao, o Gradle clonara o repositorio do Fitcloak e o compilara. As execucoes seguintes usam o build em cache.

## Como funciona

- A versao na dependencia (`0.1.0`) deve corresponder a uma **git tag** no repositorio do Fitcloak.
- O Gradle clona o repositorio no seu cache interno (`~/.gradle/`), compila-o e disponibiliza as classes no seu classpath.
- A tarefa `preview` inicia o `fitcloak.PreviewServer` — o mesmo servidor que e executado via `./gradlew run` no proprio repositorio do Fitcloak.

## Configuracao do servidor

O servidor de pre-visualizacao le `config.json` do **diretorio de trabalho** (raiz do seu projeto). Crie-o da mesma forma descrita em [Configuracao](./configuration):

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Voce tambem precisara dos temas base do Keycloak. Baixe-os usando o [script de configuracao](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) ou copie-os de uma instalacao existente do Fitcloak.
