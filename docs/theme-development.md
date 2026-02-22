---
title: Theme Development
nav_order: 6
---

# Theme Development

## Theme structure

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Override login page
    register.ftl              # Override registration
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Theme-specific test data
```

## Template helpers

Fitcloak provides mock implementations of Keycloak's FreeMarker objects:

| Helper | Behavior |
|--------|----------|
| `${msg("key")}` | Localized message from `.properties` files |
| `${advancedMsg("key")}` | Same as `msg` — message lookup with key as fallback |
| `${kcSanitize(value)}` | Returns value as-is (mock) |
| `messagesPerField.existsError('field')` | Returns `false` |
| `messagesPerField.get('field')` | Returns `""` |
| `auth.showUsername()` | Returns `true` |
| `auth.showResetCredentials()` | Returns `true` |

## Keycloak Themes Reference

For comprehensive documentation on Keycloak's theming system, see the official guide:
[Keycloak Theme Development](https://www.keycloak.org/docs/latest/server_development/#_themes)
