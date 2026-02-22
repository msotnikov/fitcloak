---
title: "Developpement de themes"
nav_exclude: true
lang: fr
permalink: /fr/theme-development/
---

# Developpement de themes

## Structure du theme

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Surcharger la page de connexion
    register.ftl              # Surcharger l'inscription
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Donnees de test specifiques au theme
```

## Fonctions d'aide pour les templates

Fitcloak fournit des implementations simulees des objets FreeMarker de Keycloak :

| Fonction d'aide | Comportement |
|-----------------|-------------|
| `${msg("key")}` | Message localise depuis les fichiers `.properties` |
| `${advancedMsg("key")}` | Identique a `msg` -- recherche de message avec la cle comme valeur de repli |
| `${kcSanitize(value)}` | Retourne la valeur telle quelle (simulation) |
| `messagesPerField.existsError('field')` | Retourne `false` |
| `messagesPerField.get('field')` | Retourne `""` |
| `auth.showUsername()` | Retourne `true` |
| `auth.showResetCredentials()` | Retourne `true` |

## Reference des themes Keycloak

Pour une documentation complete sur le systeme de themes de Keycloak, consultez le guide officiel :
[Developpement de themes Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
