---
title: "البدء السريع"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/quick-start.html
---

# البدء السريع

## ما هو Fitcloak

Fitcloak هو **خادم معاينة محلي** لتطوير قوالب Keycloak. يعمل **بشكل منفصل عن Keycloak** ولا يتطلب تشغيل Keycloak نفسه.

ما يقوم به Fitcloak:

- يجمّع قوالب FreeMarker (`.ftl`) باستخدام نفس سلسلة الوراثة في Keycloak (`Base → Parent → Child`)
- يستبدل بيانات الاختبار بدلاً من بيانات Keycloak الحقيقية (realm، user، client، إلخ)
- يحمّل الأنماط والسكريبتات المحددة في `theme.properties`
- يعرض النتيجة في متصفحك على `http://localhost:3030`

عندما يصبح القالب جاهزاً — تقوم بتعبئته وتثبيته في نسخة Keycloak حقيقية.

## المتطلبات

- **Java 17+** — التبعية الوحيدة المطلوبة. يتم تنزيل Gradle تلقائياً عبر wrapper (`gradlew`).
- Node.js **غير مطلوب** للتطوير الأساسي للقوالب. إنه مطلوب فقط إذا أردت استخدام أدوات التجميع مثل Vite أو Webpack وغيرها (انظر [تكامل Vite / HMR](./vite-integration)).

## الخطوة 1: الاستنساخ والإعداد

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## الخطوة 2: تنزيل قوالب Keycloak الأساسية

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # أحدث إصدار (الفرع الرئيسي)
./setup-keycloak-themes.sh archive/release/23.0   # أو إصدار محدد
```

يقوم السكريبت بتنزيل قوالب FreeMarker وملفات CSS الخاصة بـ PatternFly من Keycloak من نفس الالتزام (commit)، لذا تتطابق الإصدارات دائماً.

## الخطوة 3: التشغيل

```bash
./gradlew run
```

افتح [http://localhost:3030](http://localhost:3030) — سترى لوحة التحكم مع قائمة بجميع القوالب المتاحة. هذا هو Fitcloak — خادم محلي يعرض قوالبك بدون Keycloak.

## الخطوة 4: استخدم قالبك الخاص

وجّه `serverConfig.theme` في `config.json` إلى مجلد قالبك:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

يجب أن يتبع مجلد القالب هيكل قوالب Keycloak القياسي:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # تجاوز صفحة تسجيل الدخول
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

لمزيد من التفاصيل حول هيكل القالب والتطوير، انظر [تطوير القوالب](./theme-development).

## الخطوة 5: توصيل الموارد مباشرة من أداة التجميع (اختياري)

حرّر config.json واضبط:
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# شغّل خادم تطوير Vite (في نافذة طرفية)
npm run dev

# شغّل Fitcloak (في نافذة طرفية أخرى)
./gradlew run
```

## الخطوة 6: تعبئة وتثبيت القالب في Keycloak

عندما يصبح القالب جاهزاً، انشره في Keycloak:

1. انسخ مجلد القالب إلى `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. أعد تشغيل Keycloak (أو أعد البناء إذا كنت تستخدم Docker).

3. في لوحة إدارة Keycloak، انتقل إلى **Realm Settings → Themes** واختر قالبك.

إذا استخدمت Vite/Webpack — قم ببناء موارد الواجهة الأمامية أولاً (`npm run build`) وتأكد من أن الملفات المُجمّعة موجودة في مجلد `resources/` الخاص بقالبك.

## تشغيل القالب التوضيحي

يتضمن المشروع قالباً توضيحياً مع تكامل Vite/SCSS وعنصر React لقياس قوة كلمة المرور. القالب التوضيحي **يتطلب Node.js** لأنه يستخدم Vite.

```bash
# تثبيت تبعيات العرض التوضيحي
cd demo && npm install && cd ..

# حرّر config.json:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# شغّل خادم تطوير Vite (نافذة طرفية 1)
cd demo && npm run dev

# شغّل Fitcloak (نافذة طرفية 2)
./gradlew run
```

افتح [http://localhost:3030](http://localhost:3030). حرّر `demo/src/theme.scss` — تظهر التغييرات فوراً عبر Vite HMR.
