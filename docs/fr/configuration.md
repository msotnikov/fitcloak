---
title: "Configuration"
nav_exclude: true
lang: fr
permalink: /fr/configuration.html
---

# Configuration

Consultez [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) pour toutes les options disponibles.

## Paramètres principaux

| Champ | Description |
|-------|-------------|
| `serverConfig.theme` | Chemin vers le répertoire de votre thème |
| `serverConfig.port` | Port du serveur |
| `serverConfig.keycloakThemesPath` | Chemin vers les thèmes de base Keycloak téléchargés |
| `serverConfig.devResourcesUrl` | URL du serveur de développement Vite/Webpack pour le HMR |
| `serverConfig.qaRealms` | Liens d'accès rapide affichés sur le tableau de bord |

## Données simulées

Les données que Keycloak transmet normalement aux templates sont fournies via JSON :

- **Globales** : `config.json` (champs de niveau racine comme `realm`, `url`, `locale`)
- **Par thème** : `<theme-dir>/mock-data.json` (surcharge les données globales)
- **Par requête** : paramètres URL (priorité la plus élevée)

Exemple : `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Routage des URL

| Modèle d'URL | Type de thème |
|--------------|---------------|
| `/*` (par défaut) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

L'extension `.ftl` est optionnelle : `/login` et `/login.ftl` fonctionnent tous les deux.
