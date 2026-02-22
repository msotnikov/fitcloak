---
title: Sviluppo Temi
nav_exclude: true
lang: it
permalink: /it/theme-development/
---

# Sviluppo Temi

## Struttura del tema

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Sovrascrivere la pagina di login
    register.ftl              # Sovrascrivere la registrazione
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Dati di test specifici del tema
```

## Helper dei template

Fitcloak fornisce implementazioni simulate degli oggetti FreeMarker di Keycloak:

| Helper | Comportamento |
|--------|---------------|
| `${msg("key")}` | Messaggio localizzato dai file `.properties` |
| `${advancedMsg("key")}` | Come `msg` -- ricerca messaggio con la chiave come fallback |
| `${kcSanitize(value)}` | Restituisce il valore cosi com'e (simulato) |
| `messagesPerField.existsError('field')` | Restituisce `false` |
| `messagesPerField.get('field')` | Restituisce `""` |
| `auth.showUsername()` | Restituisce `true` |
| `auth.showResetCredentials()` | Restituisce `true` |

## Riferimento Temi Keycloak

Per la documentazione completa sul sistema di temi di Keycloak, consulta la guida ufficiale:
[Sviluppo Temi Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
