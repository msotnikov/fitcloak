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
    login.ftl                 # Sobrescribir pagina de inicio de sesion
    register.ftl              # Sobrescribir registro
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Datos de prueba especificos del tema
```

## Helpers de plantilla

Fitcloak proporciona implementaciones simuladas de los objetos FreeMarker de Keycloak:

| Helper | Comportamiento |
|--------|----------------|
| `${msg("key")}` | Mensaje localizado desde archivos `.properties` |
| `${advancedMsg("key")}` | Igual que `msg` -- busqueda de mensaje con la clave como respaldo |
| `${kcSanitize(value)}` | Devuelve el valor tal cual (simulado) |
| `messagesPerField.existsError('field')` | Devuelve `false` |
| `messagesPerField.get('field')` | Devuelve `""` |
| `auth.showUsername()` | Devuelve `true` |
| `auth.showResetCredentials()` | Devuelve `true` |

## Referencia de Temas de Keycloak

Para documentacion completa sobre el sistema de temas de Keycloak, consulta la guia oficial:
[Desarrollo de Temas de Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
