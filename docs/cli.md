---
title: CLI Options
nav_order: 4
---

# CLI Options

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Flag | Short | Description | Default |
|------|-------|-------------|---------|
| `--port` | `-p` | Server port | `3030` (or from config) |
| `--config` | `-c` | Config file path | `config.json` |
| `--theme` | `-t` | Theme path (overrides config) | — |
| `--help` | `-h` | Show help | — |
| `--version` | `-v` | Show version | — |
