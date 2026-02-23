---
title: "خيارات سطر الأوامر"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/cli.html
---

# خيارات سطر الأوامر

```
./gradlew run --args="--port 8080"
./gradlew run --args="--theme path/to/theme"
./gradlew run --args="--config my-config.json"
./gradlew run --args="--help"
./gradlew run --args="--version"
```

| العَلَم | الاختصار | الوصف | القيمة الافتراضية |
|--------|---------|-------|------------------|
| `--port` | `-p` | منفذ الخادم | `3030` (أو من الإعدادات) |
| `--config` | `-c` | مسار ملف الإعدادات | `config.json` |
| `--theme` | `-t` | مسار القالب (يتجاوز الإعدادات) | — |
| `--help` | `-h` | عرض المساعدة | — |
| `--version` | `-v` | عرض الإصدار | — |
