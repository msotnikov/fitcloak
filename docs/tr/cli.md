---
title: "CLI Seçenekleri"
nav_exclude: true
lang: tr
permalink: /tr/cli.html
---

# CLI Seçenekleri

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Bayrak | Kısa | Açıklama | Varsayılan |
|--------|------|----------|------------|
| `--port` | `-p` | Sunucu portu | `3030` (veya yapılandırmadan) |
| `--config` | `-c` | Yapılandırma dosyası yolu | `config.json` |
| `--theme` | `-t` | Tema yolu (yapılandırmayı geçersiz kılar) | — |
| `--help` | `-h` | Yardımı göster | — |
| `--version` | `-v` | Sürümü göster | — |
