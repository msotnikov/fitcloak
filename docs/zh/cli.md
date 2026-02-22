---
title: "CLI 选项"
nav_exclude: true
lang: zh
permalink: /zh/cli/
---

# CLI 选项

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| 标志 | 简写 | 说明 | 默认值 |
|------|------|------|--------|
| `--port` | `-p` | 服务器端口 | `3030`（或来自配置文件） |
| `--config` | `-c` | 配置文件路径 | `config.json` |
| `--theme` | `-t` | 主题路径（覆盖配置） | — |
| `--help` | `-h` | 显示帮助 | — |
| `--version` | `-v` | 显示版本 | — |
