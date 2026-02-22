---
title: "Hizli Baslangic"
nav_exclude: true
lang: tr
permalink: /tr/quick-start/
---

# Hizli Baslangic

## 1. Klonlayin ve yapilandirin

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak temel temalarini indirin

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # En son surum (main dali)
./setup-keycloak-themes.sh archive/release/23.0   # Veya belirli bir surum
```

Betik, Keycloak'un FreeMarker sablonlarini ve PatternFly CSS varliklarini ayni commit'ten indirir, boylece surumler her zaman eslesir.

## 3. Demoyu deneyin

Proje, Vite/SCSS entegrasyonu ve React parola guc widget'i iceren bir demo tema icerir:

```bash
# Demo bagimliklarini yukleyin
cd demo && npm install && cd ..

# config.json dosyasini duzenleyin ve ayarlayin:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Vite gelistirme sunucusunu baslatin (bir terminalde)
cd demo && npm run dev

# Fitcloak'u baslatin (baska bir terminalde)
./gradlew run
```

[http://localhost:3030](http://localhost:3030) adresini acin. `demo/src/theme.scss` dosyasini duzenleyin ve yenileyin — degisiklikler Vite araciligiyla aninda gorunur. Giris sayfasi, Fitcloak'un Vite proxy'sinin SCSS'in yani sira JSX/React'i de destekledigini gosteren React tabanli bir parola guc gostergesi icerir.

## 4. Kendi temanizi kullanin

`config.json` dosyasindaki `serverConfig.theme` ayarini tema dizininize yonlendirin:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
