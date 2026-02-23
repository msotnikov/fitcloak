---
title: "Hızlı Başlangıç"
nav_exclude: true
lang: tr
permalink: /tr/quick-start.html
---

# Hızlı Başlangıç

## 1. Klonlayın ve yapılandırın

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak temel temalarını indirin

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # En son sürüm (main dalı)
./setup-keycloak-themes.sh archive/release/23.0   # Veya belirli bir sürüm
```

Betik, Keycloak'un FreeMarker şablonlarını ve PatternFly CSS varlıklarını aynı commit'ten indirir, böylece sürümler her zaman eşleşir.

## 3. Demoyu deneyin

Proje, Vite/SCSS entegrasyonu ve React parola güç widget'ı içeren bir demo tema içerir:

```bash
# Demo bağımlılıklarını yükleyin
cd demo && npm install && cd ..

# config.json dosyasını düzenleyin ve ayarlayın:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Vite geliştirme sunucusunu başlatın (bir terminalde)
cd demo && npm run dev

# Fitcloak'u başlatın (başka bir terminalde)
./gradlew run
```

[http://localhost:3030](http://localhost:3030) adresini açın. `demo/src/theme.scss` dosyasını düzenleyin ve yenileyin — değişiklikler Vite aracılığıyla anında görünür. Giriş sayfası, Fitcloak'un Vite proxy'sinin SCSS'in yanı sıra JSX/React'i de desteklediğini gösteren React tabanlı bir parola güç göstergesi içerir.

## 4. Kendi temanızı kullanın

`config.json` dosyasındaki `serverConfig.theme` ayarını tema dizininize yönlendirin:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
