---
title: "Быстрый старт"
nav_exclude: true
lang: ru
permalink: /ru/quick-start.html
---

# Быстрый старт

## 1. Клонирование и настройка

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Скачивание базовых тем Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Последняя версия (ветка main)
./setup-keycloak-themes.sh archive/release/23.0   # Или конкретная версия
```

Скрипт скачивает FreeMarker-шаблоны и CSS-ресурсы PatternFly из Keycloak с одного и того же коммита, поэтому версии всегда совпадают.

## 3. Попробуйте демо

Проект включает демо-тему с интеграцией Vite/SCSS и React-виджетом надёжности пароля:

```bash
# Установите зависимости демо
cd demo && npm install && cd ..

# Отредактируйте config.json и установите:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Запустите dev-сервер Vite (в одном терминале)
cd demo && npm run dev

# Запустите Fitcloak (в другом терминале)
./gradlew run
```

Откройте [http://localhost:3030](http://localhost:3030). Отредактируйте `demo/src/theme.scss` и обновите страницу — изменения отобразятся мгновенно через Vite. Страница входа включает индикатор надёжности пароля на React, демонстрирующий, что прокси Vite в Fitcloak обрабатывает JSX/React в дополнение к SCSS.

## 4. Используйте свою тему

Укажите `serverConfig.theme` в `config.json` на директорию вашей темы:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
