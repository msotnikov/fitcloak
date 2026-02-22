---
title: "Sorun Giderme"
nav_exclude: true
lang: tr
permalink: /tr/troubleshooting/
---

# Sorun Giderme

## "has evaluated to null or missing" hataları

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Bu, bir FreeMarker şablonunun sahte veride bulunmayan bir değişkene referans verdiği anlamına gelir. Gerçek bir Keycloak örneğinde bu değişkenler sunucu tarafından doldurulur — Fitcloak'ta bunları JSON aracılığıyla siz sağlarsınız.

### Çözüm 1: Eksik değişkeni sahte veriye ekleyin

Temanızın `mock-data.json` (veya `config.json`) dosyasını açın ve eksik alanı ekleyin:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Keycloak şablonlarının beklediği yaygın `realm` alanları:

| Alan | Tür | Tipik değer | Kullanan |
|------|-----|-------------|----------|
| `password` | boolean | `true` | `login.ftl` — parola formunun gösterilip gösterilmeyeceğini kontrol eder |
| `registrationAllowed` | boolean | `true` | `login.ftl` — "Kayıt Ol" bağlantısı |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "Parolanızı mı unuttunuz" bağlantısı |
| `rememberMe` | boolean | `true` | `login.ftl` — "Beni hatırla" onay kutusu |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — kullanıcı adı alanı etiketi |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — kullanıcı adı alanı etiketi |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — dil seçici |

### Çözüm 2: URL sorgu parametreleri ile istek başına geçersiz kılma

Dosyaları düzenlemeden hızlı test için kullanışlıdır:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Çözüm 3: Şablonlarınızda FreeMarker varsayılanlarını kullanın

Özel `.ftl` dosyaları yazıyorsanız, eksik değerlere karşı koruma için `!` (varsayılan) operatörünü kullanın:

```ftl
<#-- Bunun yerine: -->
<#if realm.password>

<#-- Varsayılan bir değer kullanın: -->
<#if (realm.password)!true>
```

`!` operatörü, değer eksik olduğunda bir yedek sağlar. `(realm.password)!true` şu anlama gelir: "`realm.password` varsa onu kullan, yoksa `true`".

## Bir şablonun hangi değişkenlere ihtiyaç duyduğunu bulma

Keycloak şablonları birçok değişkene referans verir (`realm`, `url`, `auth`, `login`, `social`, `properties`, vb.). Yeni bir `.ftl` dosyası geçersiz kıldığınızda veya eklediğinizde, ek sahte değerler sağlamanız gerekebilir.

**Yaklaşım:** `.ftl` dosyasına bakın, tüm `${...}` ifadelerini ve `<#if ...>` koşullarını bulun, ardından referans verilen her nesnenin sahte verinizde mevcut olduğundan emin olun. Demo'nun [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) dosyası kopyalamak için iyi bir başlangıç noktasıdır.
