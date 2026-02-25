---
title: "Gradle 依存関係"
nav_exclude: true
lang: ja
permalink: /ja/gradle-dependency.html
---

# Fitcloak を Gradle 依存関係として使用する

Fitcloak リポジトリをクローンする代わりに、[Git Source Dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies_between_subprojects.html) を使用して、自分の Gradle プロジェクトから直接接続できます。Gradle がバックグラウンドで自動的にクローンとビルドを行います。

**Keycloak テーマ用の別リポジトリ**があり、手動クローンなしで Fitcloak プレビューを実行したい場合に便利です。

## 要件

- **Java 17+**
- **Gradle 6.1+**（Gradle Wrapper 推奨）

## セットアップ

### 1. `settings.gradle` にソース依存関係を追加

```groovy
sourceControl {
    gitRepository(uri("https://github.com/msotnikov/fitcloak.git")) {
        producesModule("io.fitcloak:fitcloak")
    }
}
```

### 2. `build.gradle` に依存関係とプレビュータスクを追加

```groovy
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.fitcloak:fitcloak:0.1.0'  // バージョン = git タグ
}

tasks.register('preview', JavaExec) {
    classpath = configurations.runtimeClasspath
    mainClass = 'fitcloak.PreviewServer'
    standardInput = System.in
}
```

### 3. 実行

```bash
./gradlew preview
```

初回実行時に Gradle が Fitcloak リポジトリをクローンしてビルドします。以降の実行ではキャッシュされたビルドが使用されます。

## 仕組み

- 依存関係のバージョン（`0.1.0`）は、Fitcloak リポジトリの **git タグ** と一致する必要があります。
- Gradle はリポジトリを内部キャッシュ（`~/.gradle/`）にクローンし、ビルドして、クラスをクラスパスで利用可能にします。
- `preview` タスクは `fitcloak.PreviewServer` を起動します — Fitcloak リポジトリ自体で `./gradlew run` により実行されるのと同じサーバーです。

## 設定

プレビューサーバーは**作業ディレクトリ**（プロジェクトルート）から `config.json` を読み込みます。[設定](./configuration) セクションの説明に従って作成してください：

```json
{
  "serverConfig": {
    "theme": "my-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Keycloak のベーステーマも必要です。[セットアップスクリプト](https://github.com/msotnikov/fitcloak/blob/main/setup-keycloak-themes.sh) を使用してダウンロードするか、既存の Fitcloak インストールからコピーしてください。
