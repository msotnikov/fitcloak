---
title: "البدء السريع"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/quick-start.html
---

# البدء السريع

## 1. الاستنساخ والإعداد

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. تنزيل قوالب Keycloak الأساسية

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # أحدث إصدار (الفرع الرئيسي)
./setup-keycloak-themes.sh archive/release/23.0   # أو إصدار محدد
```

يقوم السكريبت بتنزيل قوالب FreeMarker وملفات CSS الخاصة بـ PatternFly من Keycloak من نفس الالتزام (commit)، لذا تتطابق الإصدارات دائماً.

## 3. جرّب العرض التوضيحي

يتضمن المشروع قالباً توضيحياً مع تكامل Vite/SCSS وعنصر React لقياس قوة كلمة المرور:

```bash
# تثبيت تبعيات العرض التوضيحي
cd demo && npm install && cd ..

# حرّر config.json واضبط:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# شغّل خادم تطوير Vite (في نافذة طرفية)
cd demo && npm run dev

# شغّل Fitcloak (في نافذة طرفية أخرى)
./gradlew run
```

افتح [http://localhost:3030](http://localhost:3030). حرّر `demo/src/theme.scss` وحدّث الصفحة — تظهر التغييرات فوراً عبر Vite. تتضمن صفحة تسجيل الدخول مؤشر قوة كلمة المرور المبني على React لتوضيح أن بروكسي Vite في Fitcloak يتعامل مع JSX/React بالإضافة إلى SCSS.

## 4. استخدم قالبك الخاص

وجّه `serverConfig.theme` في `config.json` إلى مجلد قالبك:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
