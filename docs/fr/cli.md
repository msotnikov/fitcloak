---
title: "Options CLI"
nav_exclude: true
lang: fr
permalink: /fr/cli.html
---

# Options CLI

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Drapeau | Court | Description | Par défaut |
|---------|-------|-------------|------------|
| `--port` | `-p` | Port du serveur | `3030` (ou depuis la configuration) |
| `--config` | `-c` | Chemin du fichier de configuration | `config.json` |
| `--theme` | `-t` | Chemin du thème (surcharge la configuration) | — |
| `--help` | `-h` | Afficher l'aide | — |
| `--version` | `-v` | Afficher la version | — |
