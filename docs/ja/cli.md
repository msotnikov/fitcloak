---
title: "CLI オプション"
nav_exclude: true
lang: ja
permalink: /ja/cli.html
---

# CLI オプション

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| フラグ | 短縮形 | 説明 | デフォルト |
|--------|--------|------|------------|
| `--port` | `-p` | サーバーポート | `3030`（または設定ファイルから） |
| `--config` | `-c` | 設定ファイルのパス | `config.json` |
| `--theme` | `-t` | テーマパス（設定をオーバーライド） | — |
| `--help` | `-h` | ヘルプを表示 | — |
| `--version` | `-v` | バージョンを表示 | — |
