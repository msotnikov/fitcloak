---
title: "設定"
nav_exclude: true
lang: ja
permalink: /ja/configuration/
---

# 設定

すべてのオプションについては [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) を参照してください。

## 主要な設定

| フィールド | 説明 |
|------------|------|
| `serverConfig.theme` | テーマディレクトリのパス |
| `serverConfig.port` | サーバーポート |
| `serverConfig.keycloakThemesPath` | ダウンロード済み Keycloak ベーステーマのパス |
| `serverConfig.devResourcesUrl` | HMR 用の Vite/Webpack 開発サーバー URL |
| `serverConfig.qaRealms` | ダッシュボードに表示されるクイックアクセスリンク |

## モックデータ

Keycloak が通常テンプレートに渡すデータは JSON で提供します：

- **グローバル**：`config.json`（`realm`、`url`、`locale` などのルートレベルフィールド）
- **テーマ単位**：`<theme-dir>/mock-data.json`（グローバル設定をオーバーライド）
- **リクエスト単位**：URL クエリパラメータ（最高優先度）

例：`http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL ルーティング

| URL パターン | テーマタイプ |
|--------------|--------------|
| `/*`（デフォルト） | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

`.ftl` 拡張子は省略可能です：`/login` と `/login.ftl` のどちらでも動作します。
