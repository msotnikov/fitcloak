---
title: "Developpement de themes"
nav_exclude: true
lang: fr
permalink: /fr/theme-development/
---

# Développement de thèmes

## Structure du thème

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
  mock-data.json              # Données de test spécifiques au thème
```

## Fonctions d'aide pour les templates

Fitcloak fournit des implémentations simulées des objets FreeMarker de Keycloak :

| Fonction d'aide | Comportement |
|-----------------|-------------|
| `${msg("key")}` | Message localisé depuis les fichiers `.properties` |
| `${advancedMsg("key")}` | Identique à `msg` -- recherche de message avec la clé comme valeur de repli |
| `${kcSanitize(value)}` | Retourne la valeur telle quelle (simulation) |
| `messagesPerField.existsError('field')` | Retourne `false` |
| `messagesPerField.get('field')` | Retourne `""` |
| `auth.showUsername()` | Retourne `true` |
| `auth.showResetCredentials()` | Retourne `true` |

## Référence des thèmes Keycloak

Pour une documentation complète sur le système de thèmes de Keycloak, consultez le guide officiel :
[Développement de thèmes Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
