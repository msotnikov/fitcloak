---
title: Opzioni CLI
nav_exclude: true
lang: it
permalink: /it/cli.html
---

# Opzioni CLI

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Flag | Abbreviazione | Descrizione | Predefinito |
|------|---------------|-------------|-------------|
| `--port` | `-p` | Porta del server | `3030` (o da config) |
| `--config` | `-c` | Percorso del file di configurazione | `config.json` |
| `--theme` | `-t` | Percorso del tema (sovrascrive config) | -- |
| `--help` | `-h` | Mostra aiuto | -- |
| `--version` | `-v` | Mostra versione | -- |
