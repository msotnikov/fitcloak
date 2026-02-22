---
title: Troubleshooting
nav_order: 7
---

# Troubleshooting

## "has evaluated to null or missing" errors

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

This means a FreeMarker template references a variable that doesn't exist in the mock data. In a real Keycloak instance these variables are populated by the server — in Fitcloak you provide them via JSON.

### Fix 1: Add the missing variable to mock data

Open your theme's `mock-data.json` (or `config.json`) and add the missing field:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Common `realm` fields that Keycloak templates expect:

| Field | Type | Typical value | Used by |
|-------|------|---------------|---------|
| `password` | boolean | `true` | `login.ftl` — controls whether the password form is shown |
| `registrationAllowed` | boolean | `true` | `login.ftl` — "Register" link |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "Forgot password" link |
| `rememberMe` | boolean | `true` | `login.ftl` — "Remember me" checkbox |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — username field label |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — username field label |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — language selector |

### Fix 2: Override per-request via URL query parameters

Useful for quick testing without editing files:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Fix 3: Use FreeMarker defaults in your templates

If you're writing custom `.ftl` files, use the `!` (default) operator to guard against missing values:

```ftl
<#-- Instead of: -->
<#if realm.password>

<#-- Use a default value: -->
<#if (realm.password)!true>
```

The `!` operator provides a fallback when the value is missing. `(realm.password)!true` means "use `realm.password` if it exists, otherwise `true`".

## Finding which variables a template needs

Keycloak templates reference many variables (`realm`, `url`, `auth`, `login`, `social`, `properties`, etc.). When you override or add a new `.ftl` file, you may need to provide additional mock values.

**Approach:** look at the `.ftl` file, find all `${...}` expressions and `<#if ...>` conditions, then make sure each referenced object exists in your mock data. The demo's [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) is a good starting point to copy from.
