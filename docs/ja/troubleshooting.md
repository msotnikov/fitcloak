---
title: "トラブルシューティング"
nav_exclude: true
lang: ja
permalink: /ja/troubleshooting/
---

# トラブルシューティング

## "has evaluated to null or missing" エラー

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

これは FreeMarker テンプレートがモックデータに存在しない変数を参照していることを意味します。実際の Keycloak インスタンスではこれらの変数はサーバーによって設定されますが、Fitcloak では JSON で提供します。

### 修正方法 1：不足している変数をモックデータに追加

テーマの `mock-data.json`（または `config.json`）を開き、不足しているフィールドを追加します：

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Keycloak テンプレートが参照する一般的な `realm` フィールド：

| フィールド | 型 | 典型的な値 | 使用箇所 |
|------------|------|------------|----------|
| `password` | boolean | `true` | `login.ftl` — パスワードフォームの表示を制御 |
| `registrationAllowed` | boolean | `true` | `login.ftl` — 「登録」リンク |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — 「パスワードを忘れた」リンク |
| `rememberMe` | boolean | `true` | `login.ftl` — 「ログイン状態を保持」チェックボックス |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — ユーザー名フィールドのラベル |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — ユーザー名フィールドのラベル |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — 言語セレクター |

### 修正方法 2：URL クエリパラメータでリクエスト単位でオーバーライド

ファイルを編集せずに素早くテストするのに便利です：

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### 修正方法 3：テンプレートで FreeMarker のデフォルト値を使用

カスタム `.ftl` ファイルを書いている場合は、`!`（デフォルト）演算子を使って欠損値を防ぎます：

```ftl
<#-- 以下の代わりに: -->
<#if realm.password>

<#-- デフォルト値を使用: -->
<#if (realm.password)!true>
```

`!` 演算子は値が欠損している場合にフォールバック値を提供します。`(realm.password)!true` は「`realm.password` が存在すればその値を使い、なければ `true` を使う」という意味です。

## テンプレートが必要とする変数を見つける

Keycloak テンプレートは多くの変数（`realm`、`url`、`auth`、`login`、`social`、`properties` など）を参照します。新しい `.ftl` ファイルをオーバーライドまたは追加する際、追加のモックデータ値の提供が必要になる場合があります。

**アプローチ：** `.ftl` ファイルを確認し、すべての `${...}` 式と `<#if ...>` 条件を見つけ、参照されている各オブジェクトがモックデータに存在することを確認します。デモの [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) をコピーの出発点として使うと良いでしょう。
