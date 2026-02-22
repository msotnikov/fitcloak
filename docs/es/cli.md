---
title: Opciones de CLI
nav_exclude: true
lang: es
permalink: /es/cli/
---

# Opciones de CLI

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Bandera | Corto | Descripcion | Por defecto |
|---------|-------|-------------|-------------|
| `--port` | `-p` | Puerto del servidor | `3030` (o desde config) |
| `--config` | `-c` | Ruta del archivo de configuracion | `config.json` |
| `--theme` | `-t` | Ruta del tema (sobrescribe config) | -- |
| `--help` | `-h` | Mostrar ayuda | -- |
| `--version` | `-v` | Mostrar version | -- |
