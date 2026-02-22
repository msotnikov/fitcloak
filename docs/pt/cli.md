---
title: Opcoes de CLI
nav_exclude: true
lang: pt
permalink: /pt/cli/
---

# Opcoes de CLI

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Flag | Abreviacao | Descricao | Padrao |
|------|------------|-----------|--------|
| `--port` | `-p` | Porta do servidor | `3030` (ou da config) |
| `--config` | `-c` | Caminho do arquivo de configuracao | `config.json` |
| `--theme` | `-t` | Caminho do tema (sobrescreve config) | -- |
| `--help` | `-h` | Mostrar ajuda | -- |
| `--version` | `-v` | Mostrar versao | -- |
