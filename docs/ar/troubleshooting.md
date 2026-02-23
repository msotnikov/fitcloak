---
title: "استكشاف الأخطاء"
nav_exclude: true
lang: ar
dir: rtl
permalink: /ar/troubleshooting.html
---

# استكشاف الأخطاء

## أخطاء "has evaluated to null or missing"

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

هذا يعني أن قالب FreeMarker يشير إلى متغير غير موجود في البيانات التجريبية. في نسخة Keycloak الحقيقية، يتم ملء هذه المتغيرات من قبل الخادم — في Fitcloak تقدمها أنت عبر JSON.

### الحل 1: أضف المتغير المفقود إلى البيانات التجريبية

افتح ملف `mock-data.json` الخاص بقالبك (أو `config.json`) وأضف الحقل المفقود:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

حقول `realm` الشائعة التي تتوقعها قوالب Keycloak:

| الحقل | النوع | القيمة النموذجية | يُستخدم في |
|-------|-------|-----------------|-----------|
| `password` | boolean | `true` | `login.ftl` — يتحكم في عرض نموذج كلمة المرور |
| `registrationAllowed` | boolean | `true` | `login.ftl` — رابط "التسجيل" |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — رابط "نسيت كلمة المرور" |
| `rememberMe` | boolean | `true` | `login.ftl` — مربع اختيار "تذكرني" |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — تسمية حقل اسم المستخدم |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — تسمية حقل اسم المستخدم |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — محدد اللغة |

### الحل 2: تجاوز لكل طلب عبر معاملات URL

مفيد للاختبار السريع دون تحرير الملفات:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### الحل 3: استخدم القيم الافتراضية في FreeMarker في قوالبك

إذا كنت تكتب ملفات `.ftl` مخصصة، استخدم عامل `!` (القيمة الافتراضية) للحماية من القيم المفقودة:

```ftl
<#-- بدلاً من: -->
<#if realm.password>

<#-- استخدم قيمة افتراضية: -->
<#if (realm.password)!true>
```

يوفر عامل `!` قيمة احتياطية عندما تكون القيمة مفقودة. `(realm.password)!true` تعني "استخدم `realm.password` إن وُجد، وإلا `true`".

## معرفة المتغيرات التي يحتاجها القالب

تشير قوالب Keycloak إلى العديد من المتغيرات (`realm`، `url`، `auth`، `login`، `social`، `properties`، إلخ.). عند تجاوز ملف `.ftl` أو إضافة ملف جديد، قد تحتاج إلى توفير قيم تجريبية إضافية.

**الطريقة:** انظر إلى ملف `.ftl`، ابحث عن جميع تعبيرات `${...}` وشروط `<#if ...>`، ثم تأكد من وجود كل كائن مُشار إليه في بياناتك التجريبية. ملف [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) الخاص بالعرض التوضيحي نقطة بداية جيدة للنسخ منها.
