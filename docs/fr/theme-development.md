---
title: "Developpement de themes"
nav_exclude: true
lang: fr
permalink: /fr/theme-development.html
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

## Empaquetage et installation dans Keycloak

Fitcloak est un outil de developpement. Quand le theme est pret, transferez-le dans Keycloak :

1. Si vous avez utilise Vite/Webpack -- construisez les ressources frontend :
   ```bash
   npm run build
   ```
   Assurez-vous que les fichiers compiles se trouvent dans le repertoire `resources/` de votre theme.

2. Copiez le repertoire du theme dans Keycloak :
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. Redemarrez Keycloak.

4. Dans la console d'administration, allez dans **Realm Settings → Themes** et selectionnez votre theme.

## Référence des thèmes Keycloak

Pour une documentation complète sur le système de thèmes de Keycloak, consultez le guide officiel :
[Développement de thèmes Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
