---
title: "Yapilandirma"
nav_exclude: true
lang: tr
permalink: /tr/configuration/
---

# Yapilandirma

Tum secenekler icin [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) dosyasina bakin.

## Temel ayarlar

| Alan | Aciklama |
|------|----------|
| `serverConfig.theme` | Tema dizininizin yolu |
| `serverConfig.port` | Sunucu portu |
| `serverConfig.keycloakThemesPath` | Indirilen Keycloak temel temalarinin yolu |
| `serverConfig.devResourcesUrl` | HMR icin Vite/Webpack gelistirme sunucusu URL'si |
| `serverConfig.qaRealms` | Kontrol panelinde gosterilen hizli erisim baglantilari |

## Sahte veri

Keycloak'un normalde sablonlara iletigi veriler JSON araciligiyla saglanir:

- **Genel**: `config.json` (kok duzeyindeki alanlar: `realm`, `url`, `locale`)
- **Tema basina**: `<theme-dir>/mock-data.json` (genel ayarlari gecersiz kilar)
- **Istek basina**: URL sorgu parametreleri (en yuksek oncelik)

Ornek: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL Yonlendirme

| URL deseni | Tema turu |
|------------|-----------|
| `/*` (varsayilan) | Giris |
| `/account/*` | Hesap |
| `/email/*` | E-posta |
| `/admin/*` | Yonetici |

`.ftl` uzantisi istege baglidir: `/login` ve `/login.ftl` ikisi de calisir.
