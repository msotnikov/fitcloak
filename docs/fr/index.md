---
title: "Accueil"
nav_exclude: true
lang: fr
permalink: /fr/
---

# Fitcloak

Un serveur de previsualisation local et leger pour developper des **themes natifs Keycloak FreeMarker** -- sans Docker, base de donnees ni instance Keycloak complete.

[Demarrage rapide](./quick-start)
[Voir sur GitHub](https://github.com/msotnikov/fitcloak)

---

## Pourquoi Fitcloak ?

Personnaliser les pages de connexion/compte/email de Keycloak implique normalement un cycle de retour penible : reconstruire le JAR, redemarrer Keycloak, vider les caches, rafraichir la page. Fitcloak elimine tout cela -- sauvegardez simplement votre fichier et voyez le resultat.

**Prenez n'importe quel template Keycloak, pointez Fitcloak dessus et commencez a travailler.** Le proxy de serveur de developpement integre vous permet d'utiliser n'importe quelle chaine d'outils frontend -- Vite, Webpack, Parcel -- avec n'importe quel framework ou preprocesseur : React, Vue, Svelte, SCSS, Tailwind, selon vos preferences. FreeMarker effectue le rendu de la structure de page, vos outils gerent le frontend, et le HMR maintient un retour instantane.

Cela vous offre toute la flexibilite du developpement frontend moderne tout en restant dans le systeme natif de themes Keycloak : pas de SPI personnalises, pas de dependance fournisseur -- juste des templates `.ftl` standard qui se deploient sur n'importe quelle instance Keycloak tels quels.

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Approche** | Templates FreeMarker natifs + outillage frontend au choix | Composants React compiles en themes |
| **Cas d'usage** | Personnalisation des themes Keycloak standard avec une DX moderne | Creation d'interfaces entierement nouvelles avec React |
| **Frontend** | N'importe quel framework (React, Vue, Svelte, Alpine.js, vanilla) ou aucun | React/TypeScript uniquement |
| **Courbe d'apprentissage** | Connaitre FreeMarker = pret a demarrer | Necessite des connaissances React/TypeScript |
| **Resultat** | Repertoire de theme standard (fonctionne sur n'importe quel Keycloak) | JAR avec application React compilee |

Keycloakify emprunte un chemin different : il remplace entierement FreeMarker par une SPA React et possede son propre pipeline de construction. Fitcloak fonctionne avec le systeme de themes standard de Keycloak -- les memes templates `.ftl`, le meme deploiement, juste un workflow de developpement bien meilleur.

## Fonctionnalites

- **Retour instantane** -- editez `.ftl` / `.css` / `.properties`, rafraichissez le navigateur
- **Heritage complet** -- emule la chaine de themes Keycloak `Base -> Parent -> Child`
- **Integration Vite/HMR** -- proxy vers un serveur de developpement pour le remplacement de modules a chaud
- **Tests dynamiques** -- surchargez n'importe quelle variable de template via les parametres URL
- **Tableau de bord** -- navigateur de templates avec visualisation de l'heritage et liens QA
- **Zero infrastructure** -- juste Java et Gradle, rien d'autre

![Tableau de bord Fitcloak](../assets/images/screenshot-dashboard.png)
![Page d'inscription Fitcloak](../assets/images/screenshot-register.png)
