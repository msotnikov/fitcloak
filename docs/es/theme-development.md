---
title: Desarrollo de Temas
nav_exclude: true
lang: es
permalink: /es/theme-development/
---

# Desarrollo de Temas

## Estructura del tema

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Sobrescribir página de inicio de sesión
    register.ftl              # Sobrescribir registro
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Datos de prueba específicos del tema
```

## Helpers de plantilla

Fitcloak proporciona implementaciones simuladas de los objetos FreeMarker de Keycloak:

| Helper | Comportamiento |
|--------|----------------|
| `${msg("key")}` | Mensaje localizado desde archivos `.properties` |
| `${advancedMsg("key")}` | Igual que `msg` — búsqueda de mensaje con la clave como respaldo |
| `${kcSanitize(value)}` | Devuelve el valor tal cual (simulado) |
| `messagesPerField.existsError('field')` | Devuelve `false` |
| `messagesPerField.get('field')` | Devuelve `""` |
| `auth.showUsername()` | Devuelve `true` |
| `auth.showResetCredentials()` | Devuelve `true` |

## Referencia de Temas de Keycloak

Para documentación completa sobre el sistema de temas de Keycloak, consulta la guía oficial:
[Desarrollo de Temas de Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
