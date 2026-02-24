---
title: "Demarrage rapide"
nav_exclude: true
lang: fr
permalink: /fr/quick-start.html
---

# Demarrage rapide

## Qu'est-ce que Fitcloak

Fitcloak est un **serveur de previsualisation local** pour le developpement de themes Keycloak. Il fonctionne **separement de Keycloak** et ne necessite pas l'execution de Keycloak lui-meme.

Ce que fait Fitcloak :

- Assemble les templates FreeMarker (`.ftl`) selon la meme chaine d'heritage que Keycloak (`Base → Parent → Child`)
- Substitue les donnees de test a la place des donnees reelles de Keycloak (realm, user, client, etc.)
- Charge les styles et scripts specifies dans `theme.properties`
- Sert le resultat dans le navigateur a `http://localhost:3030`

Quand le theme est pret -- vous l'empaquetez et l'installez dans le vrai Keycloak.

## Prerequis

- **Java 17+** -- la seule dependance obligatoire. Gradle se telecharge automatiquement via le wrapper (`gradlew`).
- Node.js **n'est pas necessaire** pour le developpement de themes basique. Il sera requis uniquement si vous souhaitez utiliser des bundlers comme Vite, Webpack, etc. (voir [Integration Vite / HMR](./vite-integration)).

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

## 3. Lancement

```bash
./gradlew run
```

Ouvrez [http://localhost:3030](http://localhost:3030) -- vous verrez le tableau de bord avec la liste de tous les templates disponibles. C'est Fitcloak -- un serveur local qui rend vos themes sans Keycloak.

## 4. Utilisez votre propre theme

Pointez `serverConfig.theme` dans `config.json` vers le repertoire de votre theme :

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

La structure du repertoire du theme doit suivre la structure standard des themes Keycloak :

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Surcharger la page de connexion
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Pour en savoir plus sur la structure et le developpement des themes, consultez [Developpement de themes](./theme-development).

## 5. Connectez les ressources directement depuis le bundler (optionnel)

Editez config.json et definissez :
`"devResourcesUrl": "http://localhost:5173/"`


```bash
# Demarrez le serveur de developpement Vite (dans un terminal)
npm run dev

# Demarrez Fitcloak (dans un autre terminal)
./gradlew run
```
## 6. Empaquetage et installation du theme dans Keycloak

Quand le theme est pret, transferez-le dans Keycloak :

1. Copiez le repertoire du theme dans `<KEYCLOAK_HOME>/themes/` :

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Redemarrez Keycloak (ou reconstruisez si vous utilisez Docker).

3. Dans la console d'administration Keycloak, allez dans **Realm Settings → Themes** et selectionnez votre theme.

Si vous avez utilise Vite/Webpack -- compilez d'abord les ressources frontend (`npm run build`) et assurez-vous que les fichiers compiles se trouvent dans le repertoire `resources/` de votre theme.

## Lancement du theme de demo

Le projet inclut un theme de demo avec integration Vite/SCSS et un widget React de robustesse de mot de passe. Pour la demo, **Node.js est requis** car Vite est utilise.

```bash
# Installer les dependances de la demo
cd demo && npm install && cd ..

# Editez config.json :
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Demarrez le serveur de developpement Vite (terminal 1)
cd demo && npm run dev

# Demarrez Fitcloak (terminal 2)
./gradlew run
```

Ouvrez [http://localhost:3030](http://localhost:3030). Editez `demo/src/theme.scss` -- les modifications s'affichent instantanement grace au HMR de Vite.
