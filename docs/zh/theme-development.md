---
title: "主题开发"
nav_exclude: true
lang: zh
permalink: /zh/theme-development/
---

# 主题开发

## 主题结构

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # 覆盖登录页面
    register.ftl              # 覆盖注册页面
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # 主题专用测试数据
```

## 模板辅助工具

Fitcloak 提供了 Keycloak FreeMarker 对象的模拟实现：

| 辅助工具 | 行为 |
|----------|------|
| `${msg("key")}` | 来自 `.properties` 文件的本地化消息 |
| `${advancedMsg("key")}` | 与 `msg` 相同 — 消息查找，以 key 作为回退值 |
| `${kcSanitize(value)}` | 原样返回值（模拟） |
| `messagesPerField.existsError('field')` | 返回 `false` |
| `messagesPerField.get('field')` | 返回 `""` |
| `auth.showUsername()` | 返回 `true` |
| `auth.showResetCredentials()` | 返回 `true` |

## Keycloak 主题参考

有关 Keycloak 主题系统的完整文档，请参见官方指南：
[Keycloak 主题开发](https://www.keycloak.org/docs/latest/server_development/#_themes)
