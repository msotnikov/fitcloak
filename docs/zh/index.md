---
title: "首页"
nav_exclude: true
lang: zh
layout: home
permalink: /zh/
---

# Fitcloak

一个轻量级的本地预览服务器，用于开发**原生 Keycloak FreeMarker 主题** — 无需 Docker、数据库或运行完整的 Keycloak 实例。

[快速开始](./quick-start){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[在 GitHub 上查看](https://github.com/msotnikov/fitcloak){: .btn .fs-5 .mb-4 .mb-md-0 .mr-2 }
[在 Patreon 上支持](https://www.patreon.com/msotnikov/gift){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## 工作原理

Fitcloak 是一个**独立于 Keycloak 运行的**本地服务器。只需要 Java 17+。

1. 创建或编辑主题 — 标准的 `.ftl` 模板、CSS、JS
2. Fitcloak 使用与 Keycloak 相同的继承链组装模板（`Base → Parent → Child`）
3. 替换测试数据并将渲染后的页面提供给浏览器
4. 主题准备好后 — 复制到 Keycloak 并在管理控制台中选择
5. 可选地，使用打包工具（Vite、Webpack 等）实现 HMR（热模块替换），使用任何 Web 框架进行更快的开发

## 为什么选择 Fitcloak？

自定义 Keycloak 的登录/账户/邮件页面通常意味着痛苦的反馈循环：重新构建 JAR、重启 Keycloak、清除缓存、刷新页面。Fitcloak 消除了所有这些步骤 — 只需保存文件即可看到结果。

**拿起任何 Keycloak 模板，将 Fitcloak 指向它，然后开始开发。** 内置的开发服务器代理意味着你可以使用任何前端工具链 — Vite、Webpack、Parcel — 以及任何框架或预处理器：React、Vue、Svelte、SCSS、Tailwind，随你喜欢。FreeMarker 渲染页面结构，你的工具处理前端，HMR 保持即时反馈。

这使你在保持 Keycloak 原生主题系统的同时，拥有现代前端开发的全部灵活性：无需自定义 SPI，无供应商锁定 — 只需标准的 `.ftl` 模板，可以原样部署到任何 Keycloak 实例。

## 功能特性

- **即时反馈** — 编辑 `.ftl` / `.css` / `.properties`，刷新浏览器
- **完整继承** — 模拟 Keycloak 的 `Base -> Parent -> Child` 主题链
- **Vite/HMR 集成** — 代理到开发服务器以实现热模块替换
- **动态测试** — 通过 URL 查询参数覆盖任何模板变量
- **仪表盘** — 带有继承关系可视化和 QA 链接的模板浏览器
- **零基础设施** — 只需 Java 和 Gradle，无需其他


### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **方案** | 原生 FreeMarker 模板 + 任何前端工具 | React 组件编译为主题 |
| **适用场景** | 使用现代开发体验自定义标准 Keycloak 主题 | 使用 React 构建全新 UI |
| **前端** | 任何框架（React、Vue、Svelte、Alpine.js、原生 JS）或不使用框架 | 仅 React/TypeScript |
| **学习曲线** | 了解 FreeMarker 即可上手 | 需要 React/TypeScript 知识 |
| **产出** | 标准主题目录（适用于任何 Keycloak） | 包含编译后 React 应用的 JAR |

Keycloakify 走了一条不同的路线：它用 React SPA 完全替代了 FreeMarker，并拥有自己的构建流程。Fitcloak 使用标准的 Keycloak 主题系统 — 相同的 `.ftl` 模板、相同的部署方式，只是开发工作流大大改善。

![Fitcloak 仪表盘](../assets/images/screenshot-dashboard.png)
![Fitcloak 注册页面](../assets/images/screenshot-register.png)
