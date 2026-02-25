---
title: "Gradle 의존성"
nav_exclude: true
lang: ko
permalink: /ko/gradle-dependency.html
---

# Fitcloak을 Gradle 의존성으로 사용하기

Fitcloak 저장소를 클론하는 대신, [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html)를 사용하여 자신의 Gradle 프로젝트에서 직접 연결할 수 있습니다. Gradle이 백그라운드에서 자동으로 클론하고 빌드합니다.

**Keycloak 테마를 위한 별도의 저장소**가 있고 수동 클론 없이 Fitcloak 미리보기를 실행하고 싶을 때 유용합니다.

## 요구 사항

- **Java 17+**
- **Gradle 6.1+** (Gradle Wrapper 권장)

## 설정

### 1. `settings.gradle`에 소스 의존성 추가

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. `build.gradle`에 의존성과 미리보기 태스크 추가

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // 버전 = git 태그
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. 실행

```bash
./gradlew preview
```

첫 실행 시 Gradle이 Fitcloak 저장소를 클론하고 빌드합니다. 이후 실행에서는 캐시된 빌드가 사용됩니다.

## 작동 방식

- 의존성의 버전(`0.1.0`)은 Fitcloak 저장소의 **git 태그**와 일치해야 합니다.
- Gradle은 저장소를 내부 캐시(`~/.gradle/`)에 클론하고 빌드하여 클래스를 클래스패스에서 사용할 수 있게 합니다.
- `preview` 태스크는 `fitcloak.PreviewServer`를 시작합니다 — Fitcloak 저장소 자체에서 `./gradlew run`으로 실행되는 것과 동일한 서버입니다.

## 구성

미리보기 서버는 **작업 디렉토리**(프로젝트 루트)에서 `config.json`을 읽습니다. [구성](./configuration) 섹션에 설명된 대로 생성하세요:

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Keycloak 기본 테마도 필요합니다. [설정 스크립트](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh)를 사용하여 다운로드하거나 기존 Fitcloak 설치에서 복사하세요.
