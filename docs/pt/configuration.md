---
title: Configuracao
nav_exclude: true
lang: pt
permalink: /pt/configuration/
---

# Configuração

Consulte [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) para todas as opções.

## Configurações principais

| Campo | Descrição |
|-------|-----------|
| `serverConfig.theme` | Caminho para o diretório do seu tema |
| `serverConfig.port` | Porta do servidor |
| `serverConfig.keycloakThemesPath` | Caminho para os temas base baixados do Keycloak |
| `serverConfig.devResourcesUrl` | URL do servidor de desenvolvimento Vite/Webpack para HMR |
| `serverConfig.qaRealms` | Links de acesso rápido mostrados no painel de controle |

## Dados simulados

Os dados que o Keycloak normalmente passa para os templates são fornecidos via JSON:

- **Global**: `config.json` (campos de nível raiz como `realm`, `url`, `locale`)
- **Por tema**: `<theme-dir>/mock-data.json` (sobrescreve os globais)
- **Por requisição**: parâmetros de consulta URL (prioridade mais alta)

Exemplo: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Roteamento de URL

| Padrão de URL | Tipo de tema |
|---------------|--------------|
| `/*` (padrão) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

A extensão `.ftl` é opcional: `/login` e `/login.ftl` ambos funcionam.
