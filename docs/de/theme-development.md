---
title: "Theme-Entwicklung"
nav_exclude: true
lang: de
permalink: /de/theme-development.html
---

# Theme-Entwicklung

## Theme-Struktur

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Login-Seite überschreiben
    register.ftl              # Registrierung überschreiben
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Theme-spezifische Testdaten
```

## Template-Hilfsfunktionen

Fitcloak stellt Mock-Implementierungen von Keycloaks FreeMarker-Objekten bereit:

| Hilfsfunktion | Verhalten |
|---------------|-----------|
| `${msg("key")}` | Lokalisierte Nachricht aus `.properties`-Dateien |
| `${advancedMsg("key")}` | Wie `msg` -- Nachrichtensuche mit Schlüssel als Fallback |
| `${kcSanitize(value)}` | Gibt den Wert unverändert zurück (Mock) |
| `messagesPerField.existsError('field')` | Gibt `false` zurück |
| `messagesPerField.get('field')` | Gibt `""` zurück |
| `auth.showUsername()` | Gibt `true` zurück |
| `auth.showResetCredentials()` | Gibt `true` zurück |

## Keycloak-Themes-Referenz

Eine umfassende Dokumentation zum Keycloak-Theming-System finden Sie im offiziellen Leitfaden:
[Keycloak Theme-Entwicklung](https://www.keycloak.org/docs/latest/server_development/#_themes)
