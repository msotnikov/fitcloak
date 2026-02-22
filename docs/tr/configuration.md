---
title: "Yapılandırma"
nav_exclude: true
lang: tr
permalink: /tr/configuration/
---

# Yapılandırma

Tüm seçenekler için [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) dosyasına bakın.

## Temel ayarlar

| Alan | Açıklama |
|------|----------|
| `serverConfig.theme` | Tema dizininizin yolu |
| `serverConfig.port` | Sunucu portu |
| `serverConfig.keycloakThemesPath` | İndirilen Keycloak temel temalarının yolu |
| `serverConfig.devResourcesUrl` | HMR için Vite/Webpack geliştirme sunucusu URL'si |
| `serverConfig.qaRealms` | Kontrol panelinde gösterilen hızlı erişim bağlantıları |

## Sahte veri

Keycloak'un normalde şablonlara ilettiği veriler JSON aracılığıyla sağlanır:

- **Genel**: `config.json` (kök düzeyindeki alanlar: `realm`, `url`, `locale`)
- **Tema başına**: `<theme-dir>/mock-data.json` (genel ayarları geçersiz kılar)
- **İstek başına**: URL sorgu parametreleri (en yüksek öncelik)

Örnek: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL Yönlendirme

| URL deseni | Tema türü |
|------------|-----------|
| `/*` (varsayılan) | Giriş |
| `/account/*` | Hesap |
| `/email/*` | E-posta |
| `/admin/*` | Yönetici |

`.ftl` uzantısı isteğe bağlıdır: `/login` ve `/login.ftl` ikisi de çalışır.
