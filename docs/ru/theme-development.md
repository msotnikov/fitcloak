---
title: "Разработка тем"
nav_exclude: true
lang: ru
permalink: /ru/theme-development/
---

# Разработка тем

## Структура темы

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Переопределение страницы входа
    register.ftl              # Переопределение регистрации
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Тестовые данные для темы
```

## Хелперы шаблонов

Fitcloak предоставляет мок-реализации FreeMarker-объектов Keycloak:

| Хелпер | Поведение |
|--------|-----------|
| `${msg("key")}` | Локализованное сообщение из `.properties`-файлов |
| `${advancedMsg("key")}` | То же, что `msg` — поиск сообщения с ключом в качестве фоллбэка |
| `${kcSanitize(value)}` | Возвращает значение как есть (мок) |
| `messagesPerField.existsError('field')` | Возвращает `false` |
| `messagesPerField.get('field')` | Возвращает `""` |
| `auth.showUsername()` | Возвращает `true` |
| `auth.showResetCredentials()` | Возвращает `true` |

## Справочник по темам Keycloak

Подробную документацию по системе тем Keycloak см. в официальном руководстве:
[Разработка тем Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
