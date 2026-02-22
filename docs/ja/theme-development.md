---
title: "テーマ開発"
nav_exclude: true
lang: ja
permalink: /ja/theme-development/
---

# テーマ開発

## テーマ構造

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # ログインページをオーバーライド
    register.ftl              # 登録ページをオーバーライド
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # テーマ固有のテストデータ
```

## テンプレートヘルパー

Fitcloak は Keycloak の FreeMarker オブジェクトのモック実装を提供します：

| ヘルパー | 動作 |
|----------|------|
| `${msg("key")}` | `.properties` ファイルからのローカライズされたメッセージ |
| `${advancedMsg("key")}` | `msg` と同じ — キーをフォールバックとするメッセージルックアップ |
| `${kcSanitize(value)}` | 値をそのまま返す（モック） |
| `messagesPerField.existsError('field')` | `false` を返す |
| `messagesPerField.get('field')` | `""` を返す |
| `auth.showUsername()` | `true` を返す |
| `auth.showResetCredentials()` | `true` を返す |

## Keycloak テーマリファレンス

Keycloak のテーマシステムに関する包括的なドキュメントについては、公式ガイドを参照してください：
[Keycloak テーマ開発](https://www.keycloak.org/docs/latest/server_development/#_themes)
