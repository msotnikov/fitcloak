---
title: "Устранение неполадок"
nav_exclude: true
lang: ru
permalink: /ru/troubleshooting/
---

# Устранение неполадок

## Ошибки "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Это означает, что FreeMarker-шаблон ссылается на переменную, которая отсутствует в мок-данных. В реальном экземпляре Keycloak эти переменные заполняются сервером — в Fitcloak вы предоставляете их через JSON.

### Исправление 1: Добавьте недостающую переменную в мок-данные

Откройте `mock-data.json` вашей темы (или `config.json`) и добавьте недостающее поле:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Распространённые поля `realm`, которые ожидают шаблоны Keycloak:

| Поле | Тип | Типичное значение | Используется в |
|------|-----|-------------------|----------------|
| `password` | boolean | `true` | `login.ftl` — управляет отображением формы пароля |
| `registrationAllowed` | boolean | `true` | `login.ftl` — ссылка "Регистрация" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — ссылка "Забыли пароль" |
| `rememberMe` | boolean | `true` | `login.ftl` — чекбокс "Запомнить меня" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — метка поля имени пользователя |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — метка поля имени пользователя |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — выбор языка |

### Исправление 2: Переопределение через параметры URL на уровне запроса

Полезно для быстрого тестирования без редактирования файлов:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Исправление 3: Используйте значения по умолчанию FreeMarker в шаблонах

Если вы пишете собственные `.ftl`-файлы, используйте оператор `!` (значение по умолчанию) для защиты от отсутствующих значений:

```ftl
<#-- Вместо: -->
<#if realm.password>

<#-- Используйте значение по умолчанию: -->
<#if (realm.password)!true>
```

Оператор `!` предоставляет запасное значение, когда значение отсутствует. `(realm.password)!true` означает "использовать `realm.password`, если существует, иначе `true`".

## Определение необходимых переменных шаблона

Шаблоны Keycloak ссылаются на множество переменных (`realm`, `url`, `auth`, `login`, `social`, `properties` и т.д.). Когда вы переопределяете или добавляете новый `.ftl`-файл, вам может потребоваться предоставить дополнительные мок-значения.

**Подход:** изучите `.ftl`-файл, найдите все выражения `${...}` и условия `<#if ...>`, затем убедитесь, что каждый упомянутый объект существует в ваших мок-данных. Файл [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) демо-темы — хорошая отправная точка для копирования.
