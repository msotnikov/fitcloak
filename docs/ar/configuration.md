---
title: "الإعدادات"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/configuration.html
---

# الإعدادات

انظر [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json) لجميع الخيارات.

## الإعدادات الرئيسية

| الحقل | الوصف |
|-------|-------|
| `serverConfig.theme` | مسار مجلد القالب الخاص بك |
| `serverConfig.port` | منفذ الخادم |
| `serverConfig.keycloakThemesPath` | مسار قوالب Keycloak الأساسية المُنزّلة |
| `serverConfig.devResourcesUrl` | رابط خادم تطوير Vite/Webpack لـ HMR |
| `serverConfig.qaRealms` | روابط الوصول السريع المعروضة على لوحة التحكم |

## البيانات التجريبية

البيانات التي يمررها Keycloak عادةً للقوالب تُقدَّم عبر JSON:

- **عامة**: `config.json` (الحقول على مستوى الجذر مثل `realm`، `url`، `locale`)
- **لكل قالب**: `<theme-dir>/mock-data.json` (تتجاوز الإعدادات العامة)
- **لكل طلب**: معاملات URL (الأولوية الأعلى)

مثال: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## توجيه الروابط

| نمط الرابط | نوع القالب |
|------------|-----------|
| `/*` (افتراضي) | تسجيل الدخول |
| `/account/*` | الحساب |
| `/email/*` | البريد الإلكتروني |
| `/admin/*` | الإدارة |

امتداد `.ftl` اختياري: `/login` و `/login.ftl` كلاهما يعملان.
