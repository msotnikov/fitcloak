---
title: "Gradle 依赖"
nav_exclude: true
lang: zh
permalink: /zh/gradle-dependency.html
---

# 将 Fitcloak 作为 Gradle 依赖使用

无需克隆 Fitcloak 仓库，您可以通过 [Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html) 从自己的 Gradle 项目直接连接。Gradle 会在后台自动克隆和构建 Fitcloak。

当您有一个**独立的 Keycloak 主题仓库**，并希望无需手动克隆即可运行 Fitcloak 预览时，这非常有用。

## 要求

- **Java 17+**
- **Gradle 6.1+**（推荐使用 Gradle Wrapper）

## 设置

### 1. 在 `settings.gradle` 中添加源依赖

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. 在 `build.gradle` 中添加依赖和预览任务

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // 版本 = git 标签
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. 运行

```bash
./gradlew preview
```

首次运行时，Gradle 会克隆 Fitcloak 仓库并构建它。后续运行使用缓存的构建。

## 工作原理

- 依赖中的版本（`0.1.0`）必须与 Fitcloak 仓库中的 **git 标签** 匹配。
- Gradle 将仓库克隆到其内部缓存（`~/.gradle/`），构建它，并使类在您的 classpath 中可用。
- `preview` 任务启动 `fitcloak.PreviewServer` —— 与在 Fitcloak 仓库本身通过 `./gradlew run` 运行的服务器相同。

## 配置

预览服务器从**工作目录**（您的项目根目录）读取 `config.json`。按照[配置](./configuration)部分的说明创建：

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

您还需要 Keycloak 基础主题。使用[安装脚本](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh)下载，或从现有的 Fitcloak 安装中复制。
