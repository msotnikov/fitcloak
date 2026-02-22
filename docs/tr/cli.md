---
title: "CLI Secenekleri"
nav_exclude: true
lang: tr
permalink: /tr/cli/
---

# CLI Secenekleri

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Bayrak | Kisa | Aciklama | Varsayilan |
|--------|------|----------|------------|
| `--port` | `-p` | Sunucu portu | `3030` (veya yapilandirmadan) |
| `--config` | `-c` | Yapilandirma dosyasi yolu | `config.json` |
| `--theme` | `-t` | Tema yolu (yapilandirmayi gecersiz kilar) | — |
| `--help` | `-h` | Yardimi goster | — |
| `--version` | `-v` | Surumu goster | — |
