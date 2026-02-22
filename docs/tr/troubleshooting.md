---
title: "Sorun Giderme"
nav_exclude: true
lang: tr
permalink: /tr/troubleshooting/
---

# Sorun Giderme

## "has evaluated to null or missing" hatalari

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

Bu, bir FreeMarker sablonunun sahte veride bulunmayan bir degiskene referans verdigi anlamina gelir. Gercek bir Keycloak orneginde bu degiskenler sunucu tarafindan doldurulur — Fitcloak'ta bunlari JSON araciligiyla siz saglarsiniz.

### Cozum 1: Eksik degiskeni sahte veriye ekleyin

Temanizin `mock-data.json` (veya `config.json`) dosyasini acin ve eksik alani ekleyin:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Keycloak sablonlarinin beklediği yaygin `realm` alanlari:

| Alan | Tur | Tipik deger | Kullanan |
|------|-----|-------------|----------|
| `password` | boolean | `true` | `login.ftl` — parola formunun gosterilip gosterilmeyecegini kontrol eder |
| `registrationAllowed` | boolean | `true` | `login.ftl` — "Kayit Ol" baglantisi |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "Parolanizi mi unuttunuz" baglantisi |
| `rememberMe` | boolean | `true` | `login.ftl` — "Beni hatirla" onay kutusu |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — kullanici adi alani etiketi |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — kullanici adi alani etiketi |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — dil secici |

### Cozum 2: URL sorgu parametreleri ile istek basina gecersiz kilma

Dosyalari duzenlemeden hizli test icin kullanislidir:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### Cozum 3: Sablonlarinizda FreeMarker varsayilanlarini kullanin

Ozel `.ftl` dosyalari yaziyorsaniz, eksik degerlere karsi koruma icin `!` (varsayilan) operatorunu kullanin:

```ftl
<#-- Bunun yerine: -->
<#if realm.password>

<#-- Varsayilan bir deger kullanin: -->
<#if (realm.password)!true>
```

`!` operatoru, deger eksik oldugunda bir yedek saglar. `(realm.password)!true` su anlama gelir: "`realm.password` varsa onu kullan, yoksa `true`".

## Bir sablonun hangi degiskenlere ihtiyac duydugunu bulma

Keycloak sablonlari bircok degiskene referans verir (`realm`, `url`, `auth`, `login`, `social`, `properties`, vb.). Yeni bir `.ftl` dosyasi gecersiz kildiginizda veya eklediginizde, ek sahte degerler saglameniz gerekebilir.

**Yaklasim:** `.ftl` dosyasina bakin, tum `${...}` ifadelerini ve `<#if ...>` kosullarini bulun, ardindan referans verilen her nesnenin sahte verinizde mevcut oldugundan emin olun. Demo'nun [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) dosyasi kopyalamak icin iyi bir baslangic noktasidir.
