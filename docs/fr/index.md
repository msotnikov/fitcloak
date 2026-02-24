---
title: "Accueil"
nav_exclude: true
lang: fr
layout: home
permalink: /fr/
---

# Fitcloak

Un serveur de prévisualisation local et léger pour développer des **thèmes natifs Keycloak FreeMarker** -- sans Docker, base de données ni instance Keycloak complète.

[Demarrage rapide](./quick-start){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[GitHub](https://github.com/msotnikov/fitcloak){: .btn .fs-5 .mb-4 .mb-md-0 .mr-2 }
[Soutenir sur Patreon](https://www.patreon.com/msotnikov/gift){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## Comment ca fonctionne

Fitcloak est un serveur local qui **fonctionne separement de Keycloak**. Seul Java 17+ est requis pour le lancement.

1. Vous creez ou modifiez un theme -- des templates `.ftl` standard, CSS, JS
2. Fitcloak assemble les templates selon la meme chaine d'heritage que Keycloak (`Base → Parent → Child`)
3. Il substitue les donnees de test et sert la page rendue dans votre navigateur
4. Quand le theme est pret -- copiez-le dans Keycloak et selectionnez-le dans la console d'administration
5. Optionnellement, utilisez des bundlers (Vite, Webpack, etc.) pour le HMR (Hot Module Reload) afin de developper plus rapidement avec n'importe quel framework web

## Pourquoi Fitcloak ?

Personnaliser les pages de connexion/compte/email de Keycloak implique normalement un cycle de retour pénible : reconstruire le JAR, redémarrer Keycloak, vider les caches, rafraîchir la page. Fitcloak élimine tout cela -- sauvegardez simplement votre fichier et voyez le résultat.

**Prenez n'importe quel template Keycloak, pointez Fitcloak dessus et commencez à travailler.** Le proxy de serveur de développement intégré vous permet d'utiliser n'importe quelle chaîne d'outils frontend -- Vite, Webpack, Parcel -- avec n'importe quel framework ou préprocesseur : React, Vue, Svelte, SCSS, Tailwind, selon vos préférences. FreeMarker effectue le rendu de la structure de page, vos outils gèrent le frontend, et le HMR maintient un retour instantané.

Cela vous offre toute la flexibilité du développement frontend moderne tout en restant dans le système natif de thèmes Keycloak : pas de SPI personnalisés, pas de dépendance fournisseur -- juste des templates `.ftl` standard qui se déploient sur n'importe quelle instance Keycloak tels quels.

## Fonctionnalités

- **Retour instantané** -- éditez `.ftl` / `.css` / `.properties`, rafraîchissez le navigateur
- **Héritage complet** -- émule la chaîne de thèmes Keycloak `Base -> Parent -> Child`
- **Intégration Vite/HMR** -- proxy vers un serveur de développement pour le remplacement de modules à chaud
- **Tests dynamiques** -- surchargez n'importe quelle variable de template via les paramètres URL
- **Tableau de bord** -- navigateur de templates avec visualisation de l'héritage et liens QA
- **Zéro infrastructure** -- juste Java et Gradle, rien d'autre


### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Approche** | Templates FreeMarker natifs + outillage frontend au choix | Composants React compilés en thèmes |
| **Cas d'usage** | Personnalisation des thèmes Keycloak standard avec une DX moderne | Création d'interfaces entièrement nouvelles avec React |
| **Frontend** | N'importe quel framework (React, Vue, Svelte, Alpine.js, vanilla) ou aucun | React/TypeScript uniquement |
| **Courbe d'apprentissage** | Connaître FreeMarker = prêt à démarrer | Nécessite des connaissances React/TypeScript |
| **Résultat** | Répertoire de thème standard (fonctionne sur n'importe quel Keycloak) | JAR avec application React compilée |

Keycloakify emprunte un chemin différent : il remplace entièrement FreeMarker par une SPA React et possède son propre pipeline de construction. Fitcloak fonctionne avec le système de thèmes standard de Keycloak -- les mêmes templates `.ftl`, le même déploiement, juste un workflow de développement bien meilleur.

### Apparence
![Tableau de bord Fitcloak](../assets/images/screenshot-dashboard.png)
![Page d'inscription Fitcloak](../assets/images/screenshot-register.png)
