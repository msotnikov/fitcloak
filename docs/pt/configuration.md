---
title: Configuracao
nav_exclude: true
lang: pt
permalink: /pt/configuration/
---

# Configuracao

Consulte [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) para todas as opcoes.

## Configuracoes principais

| Campo | Descricao |
|-------|-----------|
| `serverConfig.theme` | Caminho para o diretorio do seu tema |
| `serverConfig.port` | Porta do servidor |
| `serverConfig.keycloakThemesPath` | Caminho para os temas base baixados do Keycloak |
| `serverConfig.devResourcesUrl` | URL do servidor de desenvolvimento Vite/Webpack para HMR |
| `serverConfig.qaRealms` | Links de acesso rapido mostrados no painel de controle |

## Dados simulados

Os dados que o Keycloak normalmente passa para os templates sao fornecidos via JSON:

- **Global**: `config.json` (campos de nivel raiz como `realm`, `url`, `locale`)
- **Por tema**: `<theme-dir>/mock-data.json` (sobrescreve os globais)
- **Por requisicao**: parametros de consulta URL (prioridade mais alta)

Exemplo: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Roteamento de URL

| Padrao de URL | Tipo de tema |
|---------------|--------------|
| `/*` (padrao) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

A extensao `.ftl` e opcional: `/login` e `/login.ftl` ambos funcionam.
