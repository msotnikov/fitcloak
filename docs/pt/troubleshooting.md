---
title: Solucao de Problemas
nav_exclude: true
lang: pt
permalink: /pt/troubleshooting/
---

# Solucao de Problemas

## Erros "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Isso significa que um template FreeMarker referencia uma variavel que nao existe nos dados simulados. Em uma instancia Keycloak real, essas variaveis sao preenchidas pelo servidor -- no Fitcloak voce as fornece via JSON.

### Solucao 1: Adicionar a variavel ausente aos dados simulados

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

| Campo | Tipo | Valor tipico | Usado por |
|-------|------|--------------|-----------|
| `password` | boolean | `true` | `login.ftl` -- controla se o formulario de senha e exibido |
| `registrationAllowed` | boolean | `true` | `login.ftl` -- link "Registrar" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` -- link "Esqueci a senha" |
| `rememberMe` | boolean | `true` | `login.ftl` -- caixa "Lembrar-me" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` -- rotulo do campo de usuario |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` -- rotulo do campo de usuario |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` -- seletor de idioma |

### Solucao 2: Sobrescrever por requisicao via parametros de consulta URL

Util para testes rapidos sem editar arquivos:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Solucao 3: Usar valores padrao do FreeMarker nos seus templates

Se voce esta escrevendo arquivos `.ftl` personalizados, use o operador `!` (padrao) para se proteger contra valores ausentes:

```ftl
<#-- Em vez de: -->
<#if realm.password>

<#-- Use um valor padrao: -->
<#if (realm.password)!true>
```

O operador `!` fornece um fallback quando o valor esta ausente. `(realm.password)!true` significa "use `realm.password` se existir, caso contrario `true`".

## Descobrindo quais variaveis um template precisa

Os templates do Keycloak referenciam muitas variaveis (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). Quando voce sobrescreve ou adiciona um novo arquivo `.ftl`, pode precisar fornecer valores simulados adicionais.

**Abordagem:** examine o arquivo `.ftl`, encontre todas as expressoes `${...}` e condicoes `<#if ...>`, depois certifique-se de que cada objeto referenciado exista nos seus dados simulados. O [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) da demo e um bom ponto de partida para copiar.
