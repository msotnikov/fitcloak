---
title: "CLI-Optionen"
nav_exclude: true
lang: de
permalink: /de/cli/
---

# CLI-Optionen

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Flag | Kurz | Beschreibung | Standard |
|------|------|-------------|----------|
| `--port` | `-p` | Server-Port | `3030` (oder aus Konfiguration) |
| `--config` | `-c` | Pfad zur Konfigurationsdatei | `config.json` |
| `--theme` | `-t` | Theme-Pfad (überschreibt Konfiguration) | — |
| `--help` | `-h` | Hilfe anzeigen | — |
| `--version` | `-v` | Version anzeigen | — |
