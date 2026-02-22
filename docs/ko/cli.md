---
title: "CLI 옵션"
nav_exclude: true
lang: ko
permalink: /ko/cli/
---

# CLI 옵션

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| 플래그 | 축약형 | 설명 | 기본값 |
|--------|--------|------|--------|
| `--port` | `-p` | 서버 포트 | `3030` (또는 설정 파일에서) |
| `--config` | `-c` | 설정 파일 경로 | `config.json` |
| `--theme` | `-t` | 테마 경로 (설정 오버라이드) | — |
| `--help` | `-h` | 도움말 표시 | — |
| `--version` | `-v` | 버전 표시 | — |
