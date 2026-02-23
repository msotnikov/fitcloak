---
title: Solucion de Problemas
nav_exclude: true
lang: es
permalink: /es/troubleshooting.html
---

# Solución de Problemas

## Errores "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Esto significa que una plantilla FreeMarker hace referencia a una variable que no existe en los datos simulados. En una instancia real de Keycloak, estas variables son completadas por el servidor — en Fitcloak las proporcionas mediante JSON.

### Solución 1: Agregar la variable faltante a los datos simulados

Abre el `mock-data.json` de tu tema (o `config.json`) y agrega el campo faltante:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Campos comunes de `realm` que las plantillas de Keycloak esperan:

| Campo | Tipo | Valor típico | Usado por |
|-------|------|--------------|-----------|
| `password` | boolean | `true` | `login.ftl` — controla si se muestra el formulario de contraseña |
| `registrationAllowed` | boolean | `true` | `login.ftl` — enlace "Registrarse" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — enlace "Olvidé mi contraseña" |
| `rememberMe` | boolean | `true` | `login.ftl` — casilla "Recuérdame" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — etiqueta del campo de usuario |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — etiqueta del campo de usuario |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — selector de idioma |

### Solución 2: Sobrescribir por solicitud mediante parámetros de consulta URL

Útil para pruebas rápidas sin editar archivos:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Solución 3: Usar valores por defecto de FreeMarker en tus plantillas

Si estás escribiendo archivos `.ftl` personalizados, usa el operador `!` (por defecto) para protegerte contra valores faltantes:

```ftl
<#-- En lugar de: -->
<#if realm.password>

<#-- Usa un valor por defecto: -->
<#if (realm.password)!true>
```

El operador `!` proporciona un respaldo cuando el valor no existe. `(realm.password)!true` significa "usa `realm.password` si existe, de lo contrario `true`".

## Encontrar qué variables necesita una plantilla

Las plantillas de Keycloak hacen referencia a muchas variables (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). Cuando sobrescribes o agregas un nuevo archivo `.ftl`, puede que necesites proporcionar valores simulados adicionales.

**Enfoque:** examina el archivo `.ftl`, encuentra todas las expresiones `${...}` y condiciones `<#if ...>`, luego asegúrate de que cada objeto referenciado exista en tus datos simulados. El [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) de la demo es un buen punto de partida para copiar.
