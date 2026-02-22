---
title: Guida Rapida
nav_exclude: true
lang: it
permalink: /it/quick-start/
---

# Guida Rapida

## 1. Clona e configura

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Scarica i temi base di Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Ultima versione (branch main)
./setup-keycloak-themes.sh archive/release/23.0   # Oppure una versione specifica
```

Lo script scarica i template FreeMarker di Keycloak e le risorse CSS di PatternFly dallo stesso commit, cosi le versioni corrispondono sempre.

## 3. Prova la demo

Il progetto include un tema demo con integrazione Vite/SCSS e un widget React per la robustezza della password:

```bash
# Installa le dipendenze della demo
cd demo && npm install && cd ..

# Modifica config.json e imposta:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Avvia il server di sviluppo Vite (in un terminale)
cd demo && npm run dev

# Avvia Fitcloak (in un altro terminale)
./gradlew run
```

Apri [http://localhost:3030](http://localhost:3030). Modifica `demo/src/theme.scss` e aggiorna -- le modifiche appaiono istantaneamente tramite Vite. La pagina di login include un indicatore di robustezza della password con React per dimostrare che il proxy Vite di Fitcloak gestisce JSX/React oltre a SCSS.

## 4. Usa il tuo tema

Punta `serverConfig.theme` in `config.json` alla directory del tuo tema:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
