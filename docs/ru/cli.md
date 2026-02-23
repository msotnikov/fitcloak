---
title: "Параметры командной строки"
nav_exclude: true
lang: ru
permalink: /ru/cli.html
---

# Параметры командной строки

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| Флаг | Сокращение | Описание | По умолчанию |
|------|------------|----------|--------------|
| `--port` | `-p` | Порт сервера | `3030` (или из конфига) |
| `--config` | `-c` | Путь к файлу конфигурации | `config.json` |
| `--theme` | `-t` | Путь к теме (переопределяет конфиг) | — |
| `--help` | `-h` | Показать справку | — |
| `--version` | `-v` | Показать версию | — |
