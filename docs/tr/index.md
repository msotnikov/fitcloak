---
title: "Ana Sayfa"
nav_exclude: true
lang: tr
layout: home
permalink: /tr/
---

# Fitcloak

**Yerel Keycloak FreeMarker temaları** geliştirmek için hafif bir yerel önizleme sunucusu — Docker, veritabanı veya tam bir Keycloak örneği çalıştırmaya gerek kalmadan.

[Başla](./quick-start)
[GitHub'da Gör](https://github.com/msotnikov/fitcloak)

---

## Neden Fitcloak?

Keycloak'un giriş/hesap/e-posta sayfalarını özelleştirmek normalde zahmetli bir geri bildirim döngüsü anlamına gelir: JAR'ı yeniden derle, Keycloak'u yeniden başlat, önbelleği temizle, sayfayı yenile. Fitcloak tüm bunları ortadan kaldırır — dosyanızı kaydedin ve sonucu görün.

**Herhangi bir Keycloak şablonunu alın, Fitcloak'u ona yönlendirin ve geliştirmeye başlayın.** Yerleşik geliştirme sunucusu proxy'si sayesinde herhangi bir ön yüz araç zincirini kullanabilirsiniz — Vite, Webpack, Parcel — herhangi bir framework veya ön işlemci ile: React, Vue, Svelte, SCSS, Tailwind, ne tercih ederseniz. FreeMarker sayfa yapısını oluşturur, araçlarınız ön yüzü yönetir ve HMR geri bildirim döngüsünü anlık tutar.

Bu size modern ön yüz geliştirmenin tam esnekliğini sunarken Keycloak'un yerel tema sistemi içinde kalmanızı sağlar: özel SPI'lar yok, satıcı bağımlılık yok — sadece herhangi bir Keycloak örneğine olduğu gibi dağıtılan standart `.ftl` şablonları.

### Fitcloak ile Keycloakify Karşılaştırması

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Yaklaşım** | Yerel FreeMarker şablonları + herhangi bir ön yüz aracı | Temalara derlenen React bileşenleri |
| **Kullanım alanı** | Modern geliştirici deneyimi ile standart Keycloak temalarını özelleştirme | React ile tamamen yeni arayüzler oluşturma |
| **Ön yüz** | Herhangi bir framework (React, Vue, Svelte, Alpine.js, vanilla) veya hiçbiri | Yalnızca React/TypeScript |
| **Öğrenme eğrisi** | FreeMarker biliyorsanız = hazırsınız | React/TypeScript bilgisi gerektirir |
| **Çıktı** | Standart tema dizini (herhangi bir Keycloak'ta çalışır) | Derlenmiş React uygulamalı JAR |

Keycloakify farklı bir yol izler: FreeMarker'ı tamamen bir React SPA ile değiştirir ve kendi derleme hattına sahiptir. Fitcloak standart Keycloak tema sistemi ile çalışır — aynı `.ftl` şablonları, aynı dağıtım, sadece çok daha iyi bir geliştirme iş akışı.

## Özellikler

- **Anlık geri bildirim** — `.ftl` / `.css` / `.properties` dosyalarını düzenleyin, tarayıcıyı yenileyin
- **Tam kalıtım** — Keycloak'un `Base -> Parent -> Child` tema zincirini taklit eder
- **Vite/HMR entegrasyonu** — sıcak modül değiştirme için geliştirme sunucusu proxy'si
- **Dinamik test** — URL sorgu parametreleri aracılığıyla herhangi bir şablon değişkenini geçersiz kılın
- **Kontrol paneli** — kalıtım görselleştirmesi ve QA bağlantıları ile şablon tarayıcısı
- **Sıfır altyapı** — sadece Java ve Gradle, başka bir şey değil

![Fitcloak Kontrol Paneli](../assets/images/screenshot-dashboard.png)
![Fitcloak Kayıt Sayfası](../assets/images/screenshot-register.png)
