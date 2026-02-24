---
title: "Tema Geliştirme"
nav_exclude: true
lang: tr
permalink: /tr/theme-development.html
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

## Keycloak'a paketleme ve kurulum

Fitcloak bir geliştirme aracıdır. Tema hazır olduğunda, Keycloak'a dağıtın:

1. Vite/Webpack kullandıysanız — ön yüz kaynaklarını derleyin:
   ```bash
   npm run build
   ```
   Derlenen dosyaların temanızın `resources/` dizininde olduğundan emin olun.

2. Tema dizinini Keycloak'a kopyalayın:
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. Keycloak'u yeniden başlatın.

4. Yönetici konsolunda **Realm Settings → Themes** bölümüne gidin ve temanızı seçin.

## Keycloak Temaları Referansı

Keycloak'un tema sistemi hakkında kapsamlı dokümantasyon için resmi kılavuza bakın:
[Keycloak Tema Geliştirme](https://www.keycloak.org/docs/latest/server_development/#_themes)
