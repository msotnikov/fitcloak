---
title: "Быстрый старт"
nav_exclude: true
lang: ru
permalink: /ru/quick-start.html
---

# Быстрый старт

## Что такое Fitcloak

Fitcloak — это **локальный сервер предварительного просмотра** для разработки тем Keycloak. Он работает **отдельно от Keycloak** и не требует запуска самого Keycloak.

Что делает Fitcloak:

- Собирает шаблоны FreeMarker (`.ftl`) по той же цепочке наследования, что и Keycloak (`Base → Parent → Child`)
- Подставляет тестовые данные вместо реальных данных Keycloak (realm, user, client и т.д.)
- Загружает стили и скрипты, указанные в `theme.properties`
- Отдаёт результат в браузер на `http://localhost:3030`

Когда тема готова — вы упаковываете её и устанавливаете в настоящий Keycloak.

## Требования

- **Java 17+** — единственная обязательная зависимость. Gradle скачается автоматически через wrapper (`gradlew`).
- Node.js **не нужен** для базовой разработки тем. Он потребуется только если вы хотите использовать сборщики вроде Vite, Webpack и т.д. (см. [Интеграция с Vite / HMR](./vite-integration)).

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

## 3. Запуск

```bash
./gradlew run
```

Откройте [http://localhost:3030](http://localhost:3030) — вы увидите панель управления со списком всех доступных шаблонов. Это и есть Fitcloak — локальный сервер, который рендерит ваши темы без Keycloak.

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

Структура директории темы должна повторять стандартную структуру тем Keycloak:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Переопределение страницы входа
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Подробнее о структуре и разработке тем — в разделе [Разработка тем](./theme-development).

## 5. Подключите ресурсы напрямую из бандлера (опционально)

Отредактируйте config.json и установите:
`"devResourcesUrl": "http://localhost:5173/"`


```bash
# Запустите dev-сервер Vite (в одном терминале)
npm run dev

# Запустите Fitcloak (в другом терминале)
./gradlew run
```
## 6. Упаковка и установка темы в Keycloak

Когда тема готова, перенесите её в Keycloak:

1. Скопируйте директорию темы в `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Перезапустите Keycloak (или пересоберите, если используете Docker).

3. В админ-консоли Keycloak перейдите в **Realm Settings → Themes** и выберите вашу тему.

Если вы использовали Vite/Webpack — предварительно соберите фронтенд-ресурсы (`npm run build`) и убедитесь, что скомпилированные файлы лежат в `resources/` вашей темы.

## Запуск демо-темы

Проект включает демо-тему с интеграцией Vite/SCSS и React-виджетом надёжности пароля. Для демо **потребуется Node.js**, так как используется Vite.

```bash
# Установите зависимости демо
cd demo && npm install && cd ..

# Отредактируйте config.json:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Запустите dev-сервер Vite (терминал 1)
cd demo && npm run dev

# Запустите Fitcloak (терминал 2)
./gradlew run
```

Откройте [http://localhost:3030](http://localhost:3030). Отредактируйте `demo/src/theme.scss` — изменения отобразятся мгновенно через Vite HMR.
