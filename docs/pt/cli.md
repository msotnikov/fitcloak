---
title: Opcoes de CLI
nav_exclude: true
lang: pt
permalink: /pt/cli/
---

# Opções de CLI

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Flag | Abreviação | Descrição | Padrão |
|------|------------|-----------|--------|
| `--port` | `-p` | Porta do servidor | `3030` (ou da config) |
| `--config` | `-c` | Caminho do arquivo de configuração | `config.json` |
| `--theme` | `-t` | Caminho do tema (sobrescreve config) | -- |
| `--help` | `-h` | Mostrar ajuda | -- |
| `--version` | `-v` | Mostrar versão | -- |
