---
title: Desenvolvimento de Temas
nav_exclude: true
lang: pt
permalink: /pt/theme-development/
---

# Desenvolvimento de Temas

## Estrutura do tema

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Sobrescrever pagina de login
    register.ftl              # Sobrescrever registro
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Dados de teste especificos do tema
```

## Helpers de template

O Fitcloak fornece implementacoes simuladas dos objetos FreeMarker do Keycloak:

| Helper | Comportamento |
|--------|---------------|
| `${msg("key")}` | Mensagem localizada dos arquivos `.properties` |
| `${advancedMsg("key")}` | Igual ao `msg` -- busca de mensagem com a chave como fallback |
| `${kcSanitize(value)}` | Retorna o valor como esta (simulado) |
| `messagesPerField.existsError('field')` | Retorna `false` |
| `messagesPerField.get('field')` | Retorna `""` |
| `auth.showUsername()` | Retorna `true` |
| `auth.showResetCredentials()` | Retorna `true` |

## Referencia de Temas do Keycloak

Para documentacao completa sobre o sistema de temas do Keycloak, consulte o guia oficial:
[Desenvolvimento de Temas do Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
