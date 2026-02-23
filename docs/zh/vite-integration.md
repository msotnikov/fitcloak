---
title: "Vite / HMR 集成"
nav_exclude: true
lang: zh
permalink: /zh/vite-integration.html
---

# Vite / HMR 集成

项目中包含的 `demo/` 目录是一个带有 SCSS 和 React 的 Vite 集成工作示例。

## 工作原理

1. 在配置中设置 `devResourcesUrl`：`"http://localhost:5173/"`
2. 在 `theme.properties` 中直接引用源文件：`styles=css/login.css src/theme.scss`
3. Fitcloak 将 `/resources/*`、`/src/*`、`/@*`、`/node_modules/*` 代理到 Vite 开发服务器
4. Vite 即时编译 SCSS、JSX、TypeScript（或其他预处理器）
5. 如果开发服务器不可用，则回退到本地文件

## 在自己的主题中使用

在你的主题目录中设置一个包含 SCSS/PostCSS 等的 Vite 项目，在 `theme.properties` 中引用源文件，并将 `devResourcesUrl` 指向 Vite。

### 示例：添加 SCSS

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

创建 `vite.config.js`：

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

在 `login/theme.properties` 中引用 SCSS：

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### 示例：添加 React

演示主题展示了这种模式 — 参见 `demo/src/PasswordStrength.jsx`，这是一个增强登录页面的 React 组件。关键是在自定义 `.ftl` 模板中使用 `<script type="module">`，而不是标准的 `properties.scripts` 机制。
