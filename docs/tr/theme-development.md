---
title: "Tema Geliştirme"
nav_exclude: true
lang: tr
permalink: /tr/theme-development/
---

# Tema Geliştirme

## Tema yapısı

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Giriş sayfasını geçersiz kıl
    register.ftl              # Kayıt sayfasını geçersiz kıl
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # Temaya özel test verileri
```

## Şablon yardımcıları

Fitcloak, Keycloak'un FreeMarker nesnelerinin sahte uygulamalarını sağlar:

| Yardımcı | Davranış |
|----------|----------|
| `${msg("key")}` | `.properties` dosyalarından yerelleştirilmiş mesaj |
| `${advancedMsg("key")}` | `msg` ile aynı — anahtar yedek olarak kullanılarak mesaj arama |
| `${kcSanitize(value)}` | Değeri olduğu gibi döndürür (sahte) |
| `messagesPerField.existsError('field')` | `false` döndürür |
| `messagesPerField.get('field')` | `""` döndürür |
| `auth.showUsername()` | `true` döndürür |
| `auth.showResetCredentials()` | `true` döndürür |

## Keycloak Temaları Referansı

Keycloak'un tema sistemi hakkında kapsamlı dokümantasyon için resmi kılavuza bakın:
[Keycloak Tema Geliştirme](https://www.keycloak.org/docs/latest/server_development/#_themes)
