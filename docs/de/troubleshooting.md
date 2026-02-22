---
title: "Fehlerbehebung"
nav_exclude: true
lang: de
permalink: /de/troubleshooting/
---

# Fehlerbehebung

## Fehler "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Dies bedeutet, dass ein FreeMarker-Template eine Variable referenziert, die in den Mock-Daten nicht existiert. In einer echten Keycloak-Instanz werden diese Variablen vom Server befüllt -- in Fitcloak stellen Sie sie über JSON bereit.

### Lösung 1: Fehlende Variable zu den Mock-Daten hinzufügen

Öffnen Sie die `mock-data.json` Ihres Themes (oder `config.json`) und fügen Sie das fehlende Feld hinzu:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Häufige `realm`-Felder, die Keycloak-Templates erwarten:

| Feld | Typ | Typischer Wert | Verwendet in |
|------|-----|----------------|-------------|
| `password` | boolean | `true` | `login.ftl` -- steuert, ob das Passwort-Formular angezeigt wird |
| `registrationAllowed` | boolean | `true` | `login.ftl` -- Link "Registrieren" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` -- Link "Passwort vergessen" |
| `rememberMe` | boolean | `true` | `login.ftl` -- Checkbox "Angemeldet bleiben" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` -- Beschriftung des Benutzernamen-Felds |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` -- Beschriftung des Benutzernamen-Felds |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` -- Sprachauswahl |

### Lösung 2: Per URL-Query-Parameter pro Anfrage überschreiben

Nützlich für schnelles Testen ohne Dateien zu bearbeiten:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Lösung 3: FreeMarker-Standardwerte in Ihren Templates verwenden

Wenn Sie eigene `.ftl`-Dateien schreiben, verwenden Sie den `!`-Operator (Standardwert), um fehlende Werte abzufangen:

```ftl
<#-- Anstatt: -->
<#if realm.password>

<#-- Verwenden Sie einen Standardwert: -->
<#if (realm.password)!true>
```

Der `!`-Operator bietet einen Fallback-Wert, wenn der Wert fehlt. `(realm.password)!true` bedeutet "verwende `realm.password` wenn vorhanden, sonst `true`".

## Herausfinden, welche Variablen ein Template benötigt

Keycloak-Templates referenzieren viele Variablen (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). Wenn Sie eine neue `.ftl`-Datei überschreiben oder hinzufügen, müssen Sie möglicherweise zusätzliche Mock-Werte bereitstellen.

**Vorgehensweise:** Schauen Sie sich die `.ftl`-Datei an, finden Sie alle `${...}`-Ausdrücke und `<#if ...>`-Bedingungen, und stellen Sie dann sicher, dass jedes referenzierte Objekt in Ihren Mock-Daten existiert. Die [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) der Demo ist ein guter Ausgangspunkt zum Kopieren.
