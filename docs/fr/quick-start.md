---
title: "Demarrage rapide"
nav_exclude: true
lang: fr
permalink: /fr/quick-start.html
---

# Démarrage rapide

## 1. Cloner et configurer

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Télécharger les thèmes de base Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Dernière version (branche main)
./setup-keycloak-themes.sh archive/release/23.0   # Ou une version spécifique
```

Le script télécharge les templates FreeMarker et les ressources CSS PatternFly de Keycloak à partir du même commit, de sorte que les versions correspondent toujours.

## 3. Essayer la démo

Le projet inclut un thème de démonstration avec intégration Vite/SCSS et un widget React de robustesse de mot de passe :

```bash
# Installer les dépendances de la démo
cd demo && npm install && cd ..

# Éditez config.json et définissez :
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Démarrer le serveur de développement Vite (dans un terminal)
cd demo && npm run dev

# Démarrer Fitcloak (dans un autre terminal)
./gradlew run
```

Ouvrez [http://localhost:3030](http://localhost:3030). Éditez `demo/src/theme.scss` et rafraîchissez -- les modifications apparaissent instantanément via Vite. La page de connexion inclut un indicateur de robustesse de mot de passe basé sur React pour démontrer que le proxy Vite de Fitcloak gère JSX/React en plus de SCSS.

## 4. Utiliser votre propre thème

Pointez `serverConfig.theme` dans `config.json` vers le répertoire de votre thème :

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
