---
title: "Vite / HMR 統合"
nav_exclude: true
lang: ja
permalink: /ja/vite-integration.html
---

# Vite / HMR 統合

同梱の `demo/` ディレクトリは、SCSS と React を使った Vite 統合の実用的な例です。

## 仕組み

1. 設定で `devResourcesUrl` を指定：`"http://localhost:5173/"`
2. `theme.properties` でソースファイルを直接参照：`styles=css/login.css src/theme.scss`
3. Fitcloak が `/resources/*`、`/src/*`、`/@*`、`/node_modules/*` を Vite 開発サーバーにプロキシ
4. Vite が SCSS、JSX、TypeScript（またはその他のプリプロセッサ）をオンザフライでコンパイル
5. 開発サーバーが利用できない場合はローカルファイルにフォールバック

## 自分のテーマで使う

テーマディレクトリに SCSS/PostCSS 等を含む Vite プロジェクトをセットアップし、`theme.properties` でソースファイルを参照し、`devResourcesUrl` を Vite に向けます。

### 例：SCSS を追加

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

`vite.config.js` を作成：

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

`login/theme.properties` で SCSS を参照：

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### 例：React を追加

デモテーマがこのパターンを示しています — ログインページを強化する React コンポーネントについては `demo/src/PasswordStrength.jsx` を参照してください。ポイントは、標準の `properties.scripts` メカニズムではなく、カスタム `.ftl` テンプレートで `<script type="module">` を使用することです。
