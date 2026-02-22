---
title: "配置"
nav_exclude: true
lang: zh
permalink: /zh/configuration/
---

# 配置

所有选项请参见 [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json)。

## 关键设置

| 字段 | 说明 |
|-------|------|
| `serverConfig.theme` | 主题目录路径 |
| `serverConfig.port` | 服务器端口 |
| `serverConfig.keycloakThemesPath` | 已下载的 Keycloak 基础主题路径 |
| `serverConfig.devResourcesUrl` | 用于 HMR 的 Vite/Webpack 开发服务器 URL |
| `serverConfig.qaRealms` | 仪表盘上显示的快速访问链接 |

## 模拟数据

Keycloak 通常传递给模板的数据通过 JSON 提供：

- **全局**：`config.json`（根级字段，如 `realm`、`url`、`locale`）
- **按主题**：`<theme-dir>/mock-data.json`（覆盖全局配置）
- **按请求**：URL 查询参数（最高优先级）

示例：`http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL 路由

| URL 模式 | 主题类型 |
|-----------|----------|
| `/*`（默认） | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

`.ftl` 扩展名是可选的：`/login` 和 `/login.ftl` 都可以使用。
