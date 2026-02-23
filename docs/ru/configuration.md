---
title: "Конфигурация"
nav_exclude: true
lang: ru
permalink: /ru/configuration.html
---

# Конфигурация

Все доступные параметры см. в [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json).

## Основные настройки

| Поле | Описание |
|------|----------|
| `serverConfig.theme` | Путь к директории вашей темы |
| `serverConfig.port` | Порт сервера |
| `serverConfig.keycloakThemesPath` | Путь к скачанным базовым темам Keycloak |
| `serverConfig.devResourcesUrl` | URL dev-сервера Vite/Webpack для HMR |
| `serverConfig.qaRealms` | Ссылки быстрого доступа, отображаемые на панели управления |

## Мок-данные

Данные, которые Keycloak обычно передаёт в шаблоны, предоставляются через JSON:

- **Глобальные**: `config.json` (поля корневого уровня, такие как `realm`, `url`, `locale`)
- **На уровне темы**: `<theme-dir>/mock-data.json` (переопределяет глобальные)
- **На уровне запроса**: параметры URL (наивысший приоритет)

Пример: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## Маршрутизация URL

| Шаблон URL | Тип темы |
|------------|----------|
| `/*` (по умолчанию) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

Расширение `.ftl` необязательно: `/login` и `/login.ftl` работают одинаково.
