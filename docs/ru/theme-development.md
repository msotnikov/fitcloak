---
title: "Разработка тем"
nav_exclude: true
lang: ru
permalink: /ru/theme-development.html
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

## Упаковка и установка в Keycloak

Fitcloak — инструмент разработки. Когда тема готова, перенесите её в Keycloak:

1. Если использовали Vite/Webpack — соберите фронтенд-ресурсы:
   ```bash
   npm run build
   ```
   Убедитесь, что скомпилированные файлы лежат в директории `resources/` вашей темы.

2. Скопируйте директорию темы в Keycloak:
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. Перезапустите Keycloak.

4. В админ-консоли перейдите в **Realm Settings → Themes** и выберите вашу тему.

## Справочник по темам Keycloak

Подробную документацию по системе тем Keycloak см. в официальном руководстве:
[Разработка тем Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
