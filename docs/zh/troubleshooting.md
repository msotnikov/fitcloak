---
title: "故障排除"
nav_exclude: true
lang: zh
permalink: /zh/troubleshooting.html
---

# 故障排除

## "has evaluated to null or missing" 错误

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

这意味着 FreeMarker 模板引用了模拟数据中不存在的变量。在实际的 Keycloak 实例中，这些变量由服务器填充 — 在 Fitcloak 中，你通过 JSON 提供它们。

### 修复方法 1：将缺失的变量添加到模拟数据中

打开你的主题的 `mock-data.json`（或 `config.json`）并添加缺失的字段：

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Keycloak 模板常用的 `realm` 字段：

| 字段 | 类型 | 典型值 | 使用场景 |
|------|------|--------|----------|
| `password` | boolean | `true` | `login.ftl` — 控制是否显示密码表单 |
| `registrationAllowed` | boolean | `true` | `login.ftl` — "注册"链接 |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "忘记密码"链接 |
| `rememberMe` | boolean | `true` | `login.ftl` — "记住我"复选框 |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — 用户名字段标签 |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — 用户名字段标签 |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — 语言选择器 |

### 修复方法 2：通过 URL 查询参数按请求覆盖

适用于无需编辑文件的快速测试：

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### 修复方法 3：在模板中使用 FreeMarker 默认值

如果你正在编写自定义 `.ftl` 文件，请使用 `!`（默认值）运算符来防止缺失值：

```ftl
<#-- 不要使用: -->
<#if realm.password>

<#-- 使用默认值: -->
<#if (realm.password)!true>
```

`!` 运算符在值缺失时提供回退值。`(realm.password)!true` 表示"如果 `realm.password` 存在则使用它，否则使用 `true`"。

## 查找模板需要的变量

Keycloak 模板引用许多变量（`realm`、`url`、`auth`、`login`、`social`、`properties` 等）。当你覆盖或添加新的 `.ftl` 文件时，可能需要提供额外的模拟数据值。

**方法：** 查看 `.ftl` 文件，找到所有 `${...}` 表达式和 `<#if ...>` 条件，然后确保每个引用的对象都存在于你的模拟数据中。演示项目的 [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json) 是一个很好的复制起点。
