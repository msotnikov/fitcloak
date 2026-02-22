---
title: "Tema Gelistirme"
nav_exclude: true
lang: tr
permalink: /tr/theme-development/
---

# Tema Gelistirme

## Tema yapisi

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Giris sayfasini gecersiz kil
    register.ftl              # Kayit sayfasini gecersiz kil
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Temaya ozel test verileri
```

## Sablon yardimcilari

Fitcloak, Keycloak'un FreeMarker nesnelerinin sahte uygulamalarini saglar:

| Yardimci | Davranis |
|----------|----------|
| `${msg("key")}` | `.properties` dosyalarindan yerellestirilmis mesaj |
| `${advancedMsg("key")}` | `msg` ile ayni — anahtar yedek olarak kullanilarak mesaj arama |
| `${kcSanitize(value)}` | Degeri oldugu gibi dondurur (sahte) |
| `messagesPerField.existsError('field')` | `false` dondurur |
| `messagesPerField.get('field')` | `""` dondurur |
| `auth.showUsername()` | `true` dondurur |
| `auth.showResetCredentials()` | `true` dondurur |

## Keycloak Temalari Referansi

Keycloak'un tema sistemi hakkinda kapsamli dokumantasyon icin resmi kilavuza bakin:
[Keycloak Tema Gelistirme](https://www.keycloak.org/docs/latest/server_development/#_themes)
