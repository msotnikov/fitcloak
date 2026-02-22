---
title: Risoluzione dei Problemi
nav_exclude: true
lang: it
permalink: /it/troubleshooting/
---

# Risoluzione dei Problemi

## Errori "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Questo significa che un template FreeMarker fa riferimento a una variabile che non esiste nei dati simulati. In un'istanza Keycloak reale queste variabili vengono popolate dal server -- in Fitcloak le fornisci tramite JSON.

### Soluzione 1: Aggiungere la variabile mancante ai dati simulati

Apri il `mock-data.json` del tuo tema (o `config.json`) e aggiungi il campo mancante:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Campi `realm` comuni che i template Keycloak si aspettano:

| Campo | Tipo | Valore tipico | Usato da |
|-------|------|---------------|----------|
| `password` | boolean | `true` | `login.ftl` -- controlla se viene mostrato il modulo password |
| `registrationAllowed` | boolean | `true` | `login.ftl` -- link "Registrati" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` -- link "Password dimenticata" |
| `rememberMe` | boolean | `true` | `login.ftl` -- casella "Ricordami" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` -- etichetta del campo username |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` -- etichetta del campo username |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` -- selettore della lingua |

### Soluzione 2: Sovrascrivere per richiesta tramite parametri di query URL

Utile per test rapidi senza modificare file:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Soluzione 3: Usare valori predefiniti di FreeMarker nei template

Se stai scrivendo file `.ftl` personalizzati, usa l'operatore `!` (predefinito) per proteggerti dai valori mancanti:

```ftl
<#-- Invece di: -->
<#if realm.password>

<#-- Usa un valore predefinito: -->
<#if (realm.password)!true>
```

L'operatore `!` fornisce un fallback quando il valore è mancante. `(realm.password)!true` significa "usa `realm.password` se esiste, altrimenti `true`".

## Trovare quali variabili necessita un template

I template Keycloak fanno riferimento a molte variabili (`realm`, `url`, `auth`, `login`, `social`, `properties`, ecc.). Quando sovrascrivi o aggiungi un nuovo file `.ftl`, potresti dover fornire valori simulati aggiuntivi.

**Approccio:** esamina il file `.ftl`, trova tutte le espressioni `${...}` e le condizioni `<#if ...>`, poi assicurati che ogni oggetto referenziato esista nei tuoi dati simulati. Il [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) della demo è un buon punto di partenza da cui copiare.
