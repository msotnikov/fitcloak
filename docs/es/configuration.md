---
title: Configuracion
nav_exclude: true
lang: es
permalink: /es/configuration/
---

# Configuración

Consulta [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) para ver todas las opciones.

## Ajustes principales

| Campo | Descripción |
|-------|-------------|
| `serverConfig.theme` | Ruta al directorio de tu tema |
| `serverConfig.port` | Puerto del servidor |
| `serverConfig.keycloakThemesPath` | Ruta a los temas base descargados de Keycloak |
| `serverConfig.devResourcesUrl` | URL del servidor de desarrollo Vite/Webpack para HMR |
| `serverConfig.qaRealms` | Enlaces de acceso rápido mostrados en el panel de control |

## Datos simulados

Los datos que Keycloak normalmente pasa a las plantillas se proporcionan mediante JSON:

- **Global**: `config.json` (campos de nivel raíz como `realm`, `url`, `locale`)
- **Por tema**: `<theme-dir>/mock-data.json` (sobrescribe los globales)
- **Por solicitud**: parámetros de consulta URL (prioridad más alta)

Ejemplo: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Enrutamiento de URL

| Patrón de URL | Tipo de tema |
|----------------|--------------|
| `/*` (por defecto) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

La extensión `.ftl` es opcional: `/login` y `/login.ftl` ambos funcionan.
