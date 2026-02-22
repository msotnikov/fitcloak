---
title: Configurazione
nav_exclude: true
lang: it
permalink: /it/configuration/
---

# Configurazione

Consulta [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) per tutte le opzioni.

## Impostazioni principali

| Campo | Descrizione |
|-------|-------------|
| `serverConfig.theme` | Percorso alla directory del tuo tema |
| `serverConfig.port` | Porta del server |
| `serverConfig.keycloakThemesPath` | Percorso ai temi base scaricati di Keycloak |
| `serverConfig.devResourcesUrl` | URL del server di sviluppo Vite/Webpack per HMR |
| `serverConfig.qaRealms` | Link di accesso rapido mostrati nella dashboard |

## Dati simulati

I dati che Keycloak normalmente passa ai template vengono forniti tramite JSON:

- **Globali**: `config.json` (campi a livello root come `realm`, `url`, `locale`)
- **Per tema**: `<theme-dir>/mock-data.json` (sovrascrive i globali)
- **Per richiesta**: parametri di query URL (priorità più alta)

Esempio: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Routing URL

| Pattern URL | Tipo di tema |
|-------------|--------------|
| `/*` (predefinito) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

L'estensione `.ftl` è opzionale: `/login` e `/login.ftl` funzionano entrambi.
