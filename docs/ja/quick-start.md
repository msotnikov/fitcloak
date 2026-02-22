---
title: "クイックスタート"
nav_exclude: true
lang: ja
permalink: /ja/quick-start/
---

# クイックスタート

## 1. クローンと設定

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak ベーステーマをダウンロード

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # 最新版（main ブランチ）
./setup-keycloak-themes.sh archive/release/23.0   # または特定バージョン
```

このスクリプトは同じコミットから Keycloak の FreeMarker テンプレートと PatternFly CSS アセットをダウンロードするため、バージョンは常に一致します。

## 3. デモを試す

プロジェクトには Vite/SCSS 統合と React パスワード強度ウィジェットを備えたデモテーマが含まれています：

```bash
# デモの依存関係をインストール
cd demo && npm install && cd ..

# config.json を編集して設定：
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Vite 開発サーバーを起動（1 つ目のターミナルで）
cd demo && npm run dev

# Fitcloak を起動（2 つ目のターミナルで）
./gradlew run
```

[http://localhost:3030](http://localhost:3030) を開きます。`demo/src/theme.scss` を編集してリロードすると、Vite を通じて変更が即座に反映されます。ログインページには React ベースのパスワード強度インジケーターが含まれており、Fitcloak の Vite プロキシが SCSS に加えて JSX/React も処理できることを実証しています。

## 4. 自分のテーマを使う

`config.json` の `serverConfig.theme` を自分のテーマディレクトリに向けます：

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
