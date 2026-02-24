---
title: "تطوير القوالب"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/theme-development.html
---

# تطوير القوالب

## هيكل القالب

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # تجاوز صفحة تسجيل الدخول
    register.ftl              # تجاوز صفحة التسجيل
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # بيانات اختبار خاصة بالقالب
```

## مساعدات القوالب

يوفر Fitcloak تطبيقات تجريبية لكائنات FreeMarker الخاصة بـ Keycloak:

| المساعد | السلوك |
|---------|--------|
| `${msg("key")}` | رسالة مترجمة من ملفات `.properties` |
| `${advancedMsg("key")}` | مثل `msg` — بحث عن الرسالة مع المفتاح كقيمة احتياطية |
| `${kcSanitize(value)}` | يُرجع القيمة كما هي (تجريبي) |
| `messagesPerField.existsError('field')` | يُرجع `false` |
| `messagesPerField.get('field')` | يُرجع `""` |
| `auth.showUsername()` | يُرجع `true` |
| `auth.showResetCredentials()` | يُرجع `true` |

## تعبئة وتثبيت في Keycloak

Fitcloak هو أداة تطوير. عندما يصبح القالب جاهزاً، انشره في Keycloak:

1. إذا استخدمت Vite/Webpack — قم ببناء موارد الواجهة الأمامية:
   ```bash
   npm run build
   ```
   تأكد من أن الملفات المُجمّعة موجودة في مجلد `resources/` الخاص بقالبك.

2. انسخ مجلد القالب إلى Keycloak:
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. أعد تشغيل Keycloak.

4. في لوحة الإدارة، انتقل إلى **Realm Settings → Themes** واختر قالبك.

## مرجع قوالب Keycloak

للاطلاع على التوثيق الشامل لنظام قوالب Keycloak، راجع الدليل الرسمي:
[تطوير قوالب Keycloak](https://www.keycloak.org/docs/latest/server_development/#_themes)
