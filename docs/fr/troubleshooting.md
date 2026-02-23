---
title: "Depannage"
nav_exclude: true
lang: fr
permalink: /fr/troubleshooting.html
---

# Dépannage

## Erreurs "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Cela signifie qu'un template FreeMarker référence une variable qui n'existe pas dans les données simulées. Dans une vraie instance Keycloak, ces variables sont remplies par le serveur -- dans Fitcloak, vous les fournissez via JSON.

### Correctif 1 : Ajouter la variable manquante aux données simulées

Ouvrez le `mock-data.json` de votre thème (ou `config.json`) et ajoutez le champ manquant :

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Champs `realm` courants attendus par les templates Keycloak :

| Champ | Type | Valeur typique | Utilisé dans |
|-------|------|----------------|-------------|
| `password` | boolean | `true` | `login.ftl` -- contrôle l'affichage du formulaire de mot de passe |
| `registrationAllowed` | boolean | `true` | `login.ftl` -- lien « S'inscrire » |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` -- lien « Mot de passe oublié » |
| `rememberMe` | boolean | `true` | `login.ftl` -- case à cocher « Se souvenir de moi » |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` -- libellé du champ nom d'utilisateur |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` -- libellé du champ nom d'utilisateur |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` -- sélecteur de langue |

### Correctif 2 : Surcharger par requête via les paramètres URL

Utile pour des tests rapides sans modifier de fichiers :

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Correctif 3 : Utiliser les valeurs par défaut FreeMarker dans vos templates

Si vous écrivez des fichiers `.ftl` personnalisés, utilisez l'opérateur `!` (valeur par défaut) pour vous prémunir contre les valeurs manquantes :

```ftl
<#-- Au lieu de : -->
<#if realm.password>

<#-- Utilisez une valeur par défaut : -->
<#if (realm.password)!true>
```

L'opérateur `!` fournit une valeur de repli lorsque la valeur est manquante. `(realm.password)!true` signifie « utiliser `realm.password` s'il existe, sinon `true` ».

## Déterminer les variables nécessaires à un template

Les templates Keycloak référencent de nombreuses variables (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). Lorsque vous surchargez ou ajoutez un nouveau fichier `.ftl`, vous devrez peut-être fournir des valeurs simulées supplémentaires.

**Approche :** examinez le fichier `.ftl`, trouvez toutes les expressions `${...}` et les conditions `<#if ...>`, puis assurez-vous que chaque objet référencé existe dans vos données simulées. Le fichier [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) de la démo est un bon point de départ à copier.
