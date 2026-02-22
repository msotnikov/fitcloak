---
title: "ホーム"
nav_exclude: true
lang: ja
permalink: /ja/
---

# Fitcloak

**ネイティブ Keycloak FreeMarker テーマ**を開発するための軽量なローカルプレビューサーバー — Docker、データベース、完全な Keycloak インスタンスの実行は不要です。

[はじめる](./quick-start)
[GitHub で見る](https://github.com/msotnikov/fitcloak)

---

## なぜ Fitcloak？

Keycloak のログイン/アカウント/メールページのカスタマイズは、通常つらいフィードバックループを意味します：JAR を再ビルドし、Keycloak を再起動し、キャッシュをクリアし、ページをリロード。Fitcloak はこれらすべてを排除します — ファイルを保存するだけで結果が確認できます。

**任意の Keycloak テンプレートを選び、Fitcloak をそこに向けて、開発を始めましょう。** 組み込みの開発サーバープロキシにより、任意のフロントエンドツールチェーン — Vite、Webpack、Parcel — を、任意のフレームワークやプリプロセッサ（React、Vue、Svelte、SCSS、Tailwind など）と組み合わせて使用できます。FreeMarker がページ構造をレンダリングし、お使いのツールがフロントエンドを処理し、HMR がフィードバックループを即座に保ちます。

これにより、Keycloak のネイティブテーマシステムに留まりながら、モダンなフロントエンド開発の完全な柔軟性が得られます：カスタム SPI 不要、ベンダーロックインなし — 任意の Keycloak インスタンスにそのままデプロイできる標準的な `.ftl` テンプレートだけです。

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **アプローチ** | ネイティブ FreeMarker テンプレート + 任意のフロントエンドツール | React コンポーネントをテーマにコンパイル |
| **ユースケース** | モダンな DX で標準 Keycloak テーマをカスタマイズ | React で完全に新しい UI を構築 |
| **フロントエンド** | 任意のフレームワーク（React、Vue、Svelte、Alpine.js、バニラ JS）またはフレームワークなし | React/TypeScript のみ |
| **学習コスト** | FreeMarker を知っていればすぐに使える | React/TypeScript の知識が必要 |
| **出力** | 標準テーマディレクトリ（任意の Keycloak で動作） | コンパイル済み React アプリを含む JAR |

Keycloakify は異なるアプローチを取ります：FreeMarker を React SPA で完全に置き換え、独自のビルドパイプラインを持っています。Fitcloak は標準的な Keycloak テーマシステムで動作します — 同じ `.ftl` テンプレート、同じデプロイ方法、ただ開発ワークフローが大幅に改善されています。

## 機能

- **即座のフィードバック** — `.ftl` / `.css` / `.properties` を編集し、ブラウザをリロード
- **完全な継承** — Keycloak の `Base -> Parent -> Child` テーマチェーンをエミュレート
- **Vite/HMR 統合** — ホットモジュールリプレースメントのための開発サーバープロキシ
- **動的テスト** — URL クエリパラメータで任意のテンプレート変数をオーバーライド
- **ダッシュボード** — 継承の可視化と QA リンク付きテンプレートブラウザ
- **インフラ不要** — Java と Gradle だけ、他には何も不要

![Fitcloak ダッシュボード](../assets/images/screenshot-dashboard.png)
![Fitcloak 登録ページ](../assets/images/screenshot-register.png)
