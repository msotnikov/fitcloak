---
title: "Ana Sayfa"
nav_exclude: true
lang: tr
permalink: /tr/
---

# Fitcloak

**Yerel Keycloak FreeMarker temaları** geliştirmek icin hafif bir yerel onizleme sunucusu — Docker, veritabani veya tam bir Keycloak ornegi calistirmaya gerek kalmadan.

[Basla](./quick-start)
[GitHub'da Gor](https://github.com/msotnikov/fitcloak)

---

## Neden Fitcloak?

Keycloak'un giris/hesap/e-posta sayfalarini ozellestirmek normalde zahmetli bir geri bildirim dongusu anlamina gelir: JAR'i yeniden derle, Keycloak'u yeniden baslat, onbellegi temizle, sayfayi yenile. Fitcloak tum bunlari ortadan kaldirir — dosyanizi kaydedin ve sonucu gorun.

**Herhangi bir Keycloak sablonunu alin, Fitcloak'u ona yonlendirin ve gelistirmeye baslayin.** Yerlesik gelistirme sunucusu proxy'si sayesinde herhangi bir on yuz arac zincirini kullanabilirsiniz — Vite, Webpack, Parcel — herhangi bir framework veya on islemci ile: React, Vue, Svelte, SCSS, Tailwind, ne tercih ederseniz. FreeMarker sayfa yapisini olusturur, araclariniz on yuzu yonetir ve HMR geri bildirim dongusunu anlik tutar.

Bu size modern on yuz gelistirmenin tam esnekligini sunarken Keycloak'un yerel tema sistemi icinde kalmanizi saglar: ozel SPI'lar yok, satici bagimlilik yok — sadece herhangi bir Keycloak ornegine oldugu gibi dagitilan standart `.ftl` sablonlari.

### Fitcloak ile Keycloakify Karsilastirmasi

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Yaklasim** | Yerel FreeMarker sablonlari + herhangi bir on yuz araci | Temalara derlenen React bilesenleri |
| **Kullanim alani** | Modern gelistirici deneyimi ile standart Keycloak temalarini ozellestirme | React ile tamamen yeni arayuzler olusturma |
| **On yuz** | Herhangi bir framework (React, Vue, Svelte, Alpine.js, vanilla) veya hicbiri | Yalnizca React/TypeScript |
| **Ogrenme egrisi** | FreeMarker biliyorsaniz = hazirsiniz | React/TypeScript bilgisi gerektirir |
| **Cikti** | Standart tema dizini (herhangi bir Keycloak'ta calisir) | Derlenimis React uygulamali JAR |

Keycloakify farkli bir yol izler: FreeMarker'i tamamen bir React SPA ile degistirir ve kendi derleme hattina sahiptir. Fitcloak standart Keycloak tema sistemi ile calisir — ayni `.ftl` sablonlari, ayni dagitim, sadece cok daha iyi bir gelistirme is akisi.

## Ozellikler

- **Anlik geri bildirim** — `.ftl` / `.css` / `.properties` dosyalarini duzenleyin, tarayiciyi yenileyin
- **Tam kalitim** — Keycloak'un `Base -> Parent -> Child` tema zincirini taklit eder
- **Vite/HMR entegrasyonu** — sicak modul degistirme icin gelistirme sunucusu proxy'si
- **Dinamik test** — URL sorgu parametreleri araciligiyla herhangi bir sablon degiskenini gecersiz kilin
- **Kontrol paneli** — kalitim gorsellestirmesi ve QA baglantilari ile sablon tarayicisi
- **Sifir altyapi** — sadece Java ve Gradle, baska bir sey degil

![Fitcloak Kontrol Paneli](../assets/images/screenshot-dashboard.png)
![Fitcloak Kayit Sayfasi](../assets/images/screenshot-register.png)
