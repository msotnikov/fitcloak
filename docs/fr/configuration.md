---
title: "Configuration"
nav_exclude: true
lang: fr
permalink: /fr/configuration/
---

# Configuration

Consultez [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) pour toutes les options disponibles.

## Parametres principaux

| Champ | Description |
|-------|-------------|
| `serverConfig.theme` | Chemin vers le repertoire de votre theme |
| `serverConfig.port` | Port du serveur |
| `serverConfig.keycloakThemesPath` | Chemin vers les themes de base Keycloak telecharges |
| `serverConfig.devResourcesUrl` | URL du serveur de developpement Vite/Webpack pour le HMR |
| `serverConfig.qaRealms` | Liens d'acces rapide affiches sur le tableau de bord |

## Donnees simulees

Les donnees que Keycloak transmet normalement aux templates sont fournies via JSON :

- **Globales** : `config.json` (champs de niveau racine comme `realm`, `url`, `locale`)
- **Par theme** : `<theme-dir>/mock-data.json` (surcharge les donnees globales)
- **Par requete** : parametres URL (priorite la plus elevee)

Exemple : `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Routage des URL

| Modele d'URL | Type de theme |
|--------------|---------------|
| `/*` (par defaut) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

L'extension `.ftl` est optionnelle : `/login` et `/login.ftl` fonctionnent tous les deux.
