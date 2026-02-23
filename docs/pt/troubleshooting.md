---
title: Solucao de Problemas
nav_exclude: true
lang: pt
permalink: /pt/troubleshooting.html
---

# Solução de Problemas

## Erros "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Isso significa que um template FreeMarker referencia uma variável que não existe nos dados simulados. Em uma instância Keycloak real, essas variáveis são preenchidas pelo servidor -- no Fitcloak você as fornece via JSON.

### Solução 1: Adicionar a variável ausente aos dados simulados

Abra o `mock-data.json` do seu tema (ou `config.json`) e adicione o campo ausente:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Campos `realm` comuns que os templates do Keycloak esperam:

| Campo | Tipo | Valor típico | Usado por |
|-------|------|--------------|-----------|
| `password` | boolean | `true` | `login.ftl` -- controla se o formulário de senha é exibido |
| `registrationAllowed` | boolean | `true` | `login.ftl` -- link "Registrar" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` -- link "Esqueci a senha" |
| `rememberMe` | boolean | `true` | `login.ftl` -- caixa "Lembrar-me" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` -- rótulo do campo de usuário |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` -- rótulo do campo de usuário |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` -- seletor de idioma |

### Solução 2: Sobrescrever por requisição via parâmetros de consulta URL

Útil para testes rápidos sem editar arquivos:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Solução 3: Usar valores padrão do FreeMarker nos seus templates

Se você está escrevendo arquivos `.ftl` personalizados, use o operador `!` (padrão) para se proteger contra valores ausentes:

```ftl
<#-- Em vez de: -->
<#if realm.password>

<#-- Use um valor padrão: -->
<#if (realm.password)!true>
```

O operador `!` fornece um fallback quando o valor está ausente. `(realm.password)!true` significa "use `realm.password` se existir, caso contrário `true`".

## Descobrindo quais variáveis um template precisa

Os templates do Keycloak referenciam muitas variáveis (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). Quando você sobrescreve ou adiciona um novo arquivo `.ftl`, pode precisar fornecer valores simulados adicionais.

**Abordagem:** examine o arquivo `.ftl`, encontre todas as expressões `${...}` e condições `<#if ...>`, depois certifique-se de que cada objeto referenciado exista nos seus dados simulados. O [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) da demo é um bom ponto de partida para copiar.
