---
title: "Gradle-зависимость"
nav_exclude: true
lang: ru
permalink: /ru/gradle-dependency.html
---

# Подключение Fitcloak как Gradle-зависимости

Вместо клонирования репозитория Fitcloak можно подключить его напрямую из вашего Gradle-проекта через [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html). Gradle сам склонирует и соберёт Fitcloak в фоне.

Это удобно, когда у вас **отдельный репозиторий для темы Keycloak** и вы хотите запускать превью без ручного клонирования Fitcloak.

## Требования

- **Java 17+**
- **Gradle 6.1+** (рекомендуется использовать Gradle Wrapper)

## Настройка

### 1. Добавьте source dependency в `settings.gradle`

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. Добавьте зависимость и задачу превью в `build.gradle`

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // версия = git-тег
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. Запуск

```bash
./gradlew preview
```

При первом запуске Gradle склонирует репозиторий Fitcloak и соберёт его. Последующие запуски используют закэшированную сборку.

## Как это работает

- Версия в зависимости (`0.1.0`) должна совпадать с **git-тегом** в репозитории Fitcloak.
- Gradle клонирует репозиторий в свой внутренний кэш (`~/.gradle/`), собирает его и делает классы доступными в вашем classpath.
- Задача `preview` запускает `fitcloak.PreviewServer` — тот же сервер, что запускается через `./gradlew run` в самом репозитории Fitcloak.

## Конфигурация

Сервер превью читает `config.json` из **рабочей директории** (корень вашего проекта). Создайте его так же, как описано в разделе [Конфигурация](./configuration):

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Также потребуются базовые темы Keycloak. Скачайте их с помощью [скрипта настройки](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) или скопируйте из существующей установки Fitcloak.
