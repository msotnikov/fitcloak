---
title: Sviluppo Temi
nav_exclude: true
lang: it
permalink: /it/theme-development.html
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
| `${kcSanitize(value)}` | Restituisce il valore così com'è (simulato) |
| `messagesPerField.existsError('field')` | Restituisce `false` |
| `messagesPerField.get('field')` | Restituisce `""` |
| `auth.showUsername()` | Restituisce `true` |
| `auth.showResetCredentials()` | Restituisce `true` |

## Pacchettizzazione e installazione in Keycloak

Fitcloak e uno strumento di sviluppo. Quando il tema e pronto, trasferiscilo in Keycloak:

1. Se hai usato Vite/Webpack -- compila le risorse frontend:
   ```bash
   npm run build
   ```
   Assicurati che i file compilati si trovino nella directory `resources/` del tuo tema.

2. Copia la directory del tema in Keycloak:
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. Riavvia Keycloak.

4. Nella console di amministrazione, vai su **Realm Settings → Themes** e seleziona il tuo tema.

## Riferimento Temi Keycloak

Per la documentazione completa sul sistema di temi di Keycloak, consulta la guida ufficiale:
[Sviluppo Temi Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
