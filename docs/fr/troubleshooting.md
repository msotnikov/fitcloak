---
title: "Depannage"
nav_exclude: true
lang: fr
permalink: /fr/troubleshooting/
---

# Depannage

## Erreurs "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Cela signifie qu'un template FreeMarker reference une variable qui n'existe pas dans les donnees simulees. Dans une vraie instance Keycloak, ces variables sont remplies par le serveur -- dans Fitcloak, vous les fournissez via JSON.

### Correctif 1 : Ajouter la variable manquante aux donnees simulees

Ouvrez le `mock-data.json` de votre theme (ou `config.json`) et ajoutez le champ manquant :

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Champs `realm` courants attendus par les templates Keycloak :

| Champ | Type | Valeur typique | Utilise dans |
|-------|------|----------------|-------------|
| `password` | boolean | `true` | `login.ftl` -- controle l'affichage du formulaire de mot de passe |
| `registrationAllowed` | boolean | `true` | `login.ftl` -- lien "S'inscrire" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` -- lien "Mot de passe oublie" |
| `rememberMe` | boolean | `true` | `login.ftl` -- case a cocher "Se souvenir de moi" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` -- libelle du champ nom d'utilisateur |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` -- libelle du champ nom d'utilisateur |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` -- selecteur de langue |

### Correctif 2 : Surcharger par requete via les parametres URL

Utile pour des tests rapides sans modifier de fichiers :

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Correctif 3 : Utiliser les valeurs par defaut FreeMarker dans vos templates

Si vous ecrivez des fichiers `.ftl` personnalises, utilisez l'operateur `!` (valeur par defaut) pour vous premunir contre les valeurs manquantes :

```ftl
<#-- Au lieu de : -->
<#if realm.password>

<#-- Utilisez une valeur par defaut : -->
<#if (realm.password)!true>
```

L'operateur `!` fournit une valeur de repli lorsque la valeur est manquante. `(realm.password)!true` signifie "utiliser `realm.password` s'il existe, sinon `true`".

## Determiner les variables necessaires a un template

Les templates Keycloak referencent de nombreuses variables (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). Lorsque vous surchargez ou ajoutez un nouveau fichier `.ftl`, vous devrez peut-etre fournir des valeurs simulees supplementaires.

**Approche :** examinez le fichier `.ftl`, trouvez toutes les expressions `${...}` et les conditions `<#if ...>`, puis assurez-vous que chaque objet reference existe dans vos donnees simulees. Le fichier [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) de la demo est un bon point de depart a copier.
