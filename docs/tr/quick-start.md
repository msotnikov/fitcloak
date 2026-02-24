---
title: "Hızlı Başlangıç"
nav_exclude: true
lang: tr
permalink: /tr/quick-start.html
---

# Hızlı Başlangıç

## Fitcloak nedir

Fitcloak, Keycloak temaları geliştirmek için bir **yerel önizleme sunucusudur**. **Keycloak'tan bağımsız** çalışır ve Keycloak'un çalışmasını gerektirmez.

Fitcloak ne yapar:

- FreeMarker şablonlarını (`.ftl`) Keycloak ile aynı kalıtım zincirini kullanarak birleştirir (`Base → Parent → Child`)
- Gerçek Keycloak verileri (realm, user, client, vb.) yerine test verilerini yerleştirir
- `theme.properties` dosyasında belirtilen stilleri ve betikleri yükler
- Sonucu tarayıcınıza `http://localhost:3030` adresinde sunar

Tema hazır olduğunda — paketlersiniz ve gerçek bir Keycloak örneğine kurarsınız.

## Gereksinimler

- **Java 17+** — tek gerekli bağımlılık. Gradle, wrapper (`gradlew`) aracılığıyla otomatik olarak indirilir.
- Node.js temel tema geliştirme için **gerekli değildir**. Yalnızca Vite, Webpack gibi paketleyiciler kullanmak istiyorsanız gereklidir (bkz. [Vite / HMR Entegrasyonu](./vite-integration)).

## Adım 1: Klonlayın ve yapılandırın

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## Adım 2: Keycloak temel temalarını indirin

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # En son sürüm (main dalı)
./setup-keycloak-themes.sh archive/release/23.0   # Veya belirli bir sürüm
```

Betik, Keycloak'un FreeMarker şablonlarını ve PatternFly CSS varlıklarını aynı commit'ten indirir, böylece sürümler her zaman eşleşir.

## Adım 3: Çalıştırın

```bash
./gradlew run
```

[http://localhost:3030](http://localhost:3030) adresini açın — tüm mevcut şablonların listesini içeren kontrol panelini göreceksiniz. Bu Fitcloak'tur — temalarınızı Keycloak olmadan oluşturan yerel bir sunucu.

## Adım 4: Kendi temanızı kullanın

`config.json` dosyasındaki `serverConfig.theme` ayarını tema dizininize yönlendirin:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Tema dizininiz standart Keycloak tema yapısını izlemelidir:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Giriş sayfasını geçersiz kıl
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Tema yapısı ve geliştirme hakkında daha fazla bilgi için bkz. [Tema Geliştirme](./theme-development).

## Adım 5: Paketleyiciden doğrudan kaynakları bağlayın (isteğe bağlı)

config.json dosyasını düzenleyin ve ayarlayın:
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# Vite geliştirme sunucusunu başlatın (terminal 1)
npm run dev

# Fitcloak'u başlatın (terminal 2)
./gradlew run
```

## Adım 6: Temayı Keycloak'a paketleyin ve kurun

Tema hazır olduğunda, Keycloak'a dağıtın:

1. Tema dizinini `<KEYCLOAK_HOME>/themes/` dizinine kopyalayın:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Keycloak'u yeniden başlatın (veya Docker kullanıyorsanız yeniden derleyin).

3. Keycloak yönetici konsolunda **Realm Settings → Themes** bölümüne gidin ve temanızı seçin.

Vite/Webpack kullandıysanız — önce ön yüz kaynaklarını derleyin (`npm run build`) ve derlenen dosyaların temanızın `resources/` dizininde olduğundan emin olun.

## Demo temayı çalıştırma

Proje, Vite/SCSS entegrasyonu ve React parola güç widget'ı içeren bir demo tema içerir. Demo **Node.js gerektirir** çünkü Vite kullanır.

```bash
# Demo bağımlılıklarını yükleyin
cd demo && npm install && cd ..

# config.json dosyasını düzenleyin:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Vite geliştirme sunucusunu başlatın (terminal 1)
cd demo && npm run dev

# Fitcloak'u başlatın (terminal 2)
./gradlew run
```

[http://localhost:3030](http://localhost:3030) adresini açın. `demo/src/theme.scss` dosyasını düzenleyin — değişiklikler Vite HMR aracılığıyla anında görünür.
