---
title: "クイックスタート"
nav_exclude: true
lang: ja
permalink: /ja/quick-start.html
---

# クイックスタート

## Fitcloak とは

Fitcloak は Keycloak テーマを開発するための**ローカルプレビューサーバー**です。**Keycloak とは別に動作**し、Keycloak 自体の実行は不要です。

Fitcloak の機能：

- Keycloak と同じ継承チェーン（`Base → Parent → Child`）を使用して FreeMarker テンプレート（`.ftl`）を組み立てます
- 実際の Keycloak データ（realm、ユーザー、クライアントなど）の代わりにテストデータを代入します
- `theme.properties` で指定されたスタイルとスクリプトをロードします
- `http://localhost:3030` でブラウザに結果を配信します

テーマの準備ができたら — パッケージ化して実際の Keycloak インスタンスにインストールします。

## 要件

- **Java 17+** — 唯一の必須依存関係です。Gradle はラッパー（`gradlew`）経由で自動的にダウンロードされます。
- Node.js は基本的なテーマ開発には**不要**です。Vite、Webpack などのバンドラーを使用する場合にのみ必要です。

## ステップ 1：クローンと設定

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## ステップ 2：Keycloak ベーステーマをダウンロード

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # 最新版（main ブランチ）
./setup-keycloak-themes.sh archive/release/23.0   # または特定バージョン
```

このスクリプトは同じコミットから Keycloak の FreeMarker テンプレートと PatternFly CSS をダウンロードするため、バージョンは常に一致します。

## ステップ 3：実行

```bash
./gradlew run
```

[http://localhost:3030](http://localhost:3030) を開きます — 利用可能なすべてのテンプレートのリストを含むダッシュボードが表示されます。これが Fitcloak です — Keycloak なしでテーマをレンダリングするローカルサーバーです。

## ステップ 4：自分のテーマを使う

`config.json` の `serverConfig.theme` を自分のテーマディレクトリに向けます：

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

テーマディレクトリは標準的な Keycloak テーマ構造に従う必要があります：

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # ログインページをオーバーライド
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

## ステップ 5：バンドラーからリソースを接続する（オプション）

config.json を編集して以下を設定します：
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# Vite 開発サーバーを起動（ターミナル 1）
npm run dev

# Fitcloak を起動（ターミナル 2）
./gradlew run
```

## ステップ 6：テーマをパッケージ化して Keycloak にインストール

テーマの準備ができたら、Keycloak にデプロイします：

1. テーマディレクトリを `<KEYCLOAK_HOME>/themes/` にコピーします：

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Keycloak を再起動します（Docker を使用している場合は再ビルド）。

3. Keycloak 管理コンソールで **Realm Settings → Themes** に移動し、テーマを選択します。

Vite/Webpack を使用した場合は、先にフロントエンドリソースをビルドしてください（`npm run build`）。コンパイルされたファイルがテーマの `resources/` ディレクトリにあることを確認してください。

## デモテーマの実行

プロジェクトには Vite/SCSS 統合と React パスワード強度ウィジェットを備えたデモテーマが含まれています。デモには Vite を使用するため **Node.js が必要**です。

```bash
# デモの依存関係をインストール
cd demo && npm install && cd ..

# config.json を編集して設定：
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Vite 開発サーバーを起動（ターミナル 1）
cd demo && npm run dev

# Fitcloak を起動（ターミナル 2）
./gradlew run
```

[http://localhost:3030](http://localhost:3030) を開きます。`demo/src/theme.scss` を編集すると — Vite HMR により変更が即座に反映されます。
