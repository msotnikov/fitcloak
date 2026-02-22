---
title: "Demarrage rapide"
nav_exclude: true
lang: fr
permalink: /fr/quick-start/
---

# Demarrage rapide

## 1. Cloner et configurer

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Telecharger les themes de base Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Derniere version (branche main)
./setup-keycloak-themes.sh archive/release/23.0   # Ou une version specifique
```

Le script telecharge les templates FreeMarker et les ressources CSS PatternFly de Keycloak a partir du meme commit, de sorte que les versions correspondent toujours.

## 3. Essayer la demo

Le projet inclut un theme de demonstration avec integration Vite/SCSS et un widget React de robustesse de mot de passe :

```bash
# Installer les dependances de la demo
cd demo && npm install && cd ..

# Editez config.json et definissez :
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Demarrer le serveur de developpement Vite (dans un terminal)
cd demo && npm run dev

# Demarrer Fitcloak (dans un autre terminal)
./gradlew run
```

Ouvrez [http://localhost:3030](http://localhost:3030). Editez `demo/src/theme.scss` et rafraichissez -- les modifications apparaissent instantanement via Vite. La page de connexion inclut un indicateur de robustesse de mot de passe base sur React pour demontrer que le proxy Vite de Fitcloak gere JSX/React en plus de SCSS.

## 4. Utiliser votre propre theme

Pointez `serverConfig.theme` dans `config.json` vers le repertoire de votre theme :

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
