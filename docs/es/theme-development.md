---
title: Desarrollo de Temas
nav_exclude: true
lang: es
permalink: /es/theme-development.html
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

## Empaquetado e instalacion en Keycloak

Fitcloak es una herramienta de desarrollo. Cuando el tema este listo, transferelo a Keycloak:

1. Si usaste Vite/Webpack -- compila los recursos frontend:
   ```bash
   npm run build
   ```
   Asegurate de que los archivos compilados esten en el directorio `resources/` de tu tema.

2. Copia el directorio del tema a Keycloak:
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. Reinicia Keycloak.

4. En la consola de administracion, ve a **Realm Settings → Themes** y selecciona tu tema.

## Referencia de Temas de Keycloak

Para documentacion completa sobre el sistema de temas de Keycloak, consulta la guia oficial:
[Desarrollo de Temas de Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
