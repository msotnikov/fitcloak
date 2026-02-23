---
title: "CLI विकल्प"
nav_exclude: true
lang: hi
permalink: /hi/cli.html
---

# CLI विकल्प

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| फ़्लैग | शॉर्ट | विवरण | डिफ़ॉल्ट |
|--------|-------|--------|---------|
| `--port` | `-p` | सर्वर पोर्ट | `3030` (या कॉन्फ़िग से) |
| `--config` | `-c` | कॉन्फ़िग फ़ाइल पथ | `config.json` |
| `--theme` | `-t` | थीम पथ (कॉन्फ़िग को ओवरराइड करता है) | — |
| `--help` | `-h` | सहायता दिखाएँ | — |
| `--version` | `-v` | संस्करण दिखाएँ | — |
