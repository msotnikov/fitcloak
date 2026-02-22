---
title: "快速开始"
nav_exclude: true
lang: zh
permalink: /zh/quick-start/
---

# 快速开始

## 1. 克隆并配置

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. 下载 Keycloak 基础主题

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # 最新版本（main 分支）
./setup-keycloak-themes.sh archive/release/23.0   # 或指定版本
```

该脚本从同一个提交中下载 Keycloak 的 FreeMarker 模板和 PatternFly CSS 资源，因此版本始终匹配。

## 3. 试用演示

项目包含一个带有 Vite/SCSS 集成和 React 密码强度组件的演示主题：

```bash
# 安装演示依赖
cd demo && npm install && cd ..

# 编辑 config.json 并设置：
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# 启动 Vite 开发服务器（在一个终端中）
cd demo && npm run dev

# 启动 Fitcloak（在另一个终端中）
./gradlew run
```

打开 [http://localhost:3030](http://localhost:3030)。编辑 `demo/src/theme.scss` 并刷新 — 更改通过 Vite 即时生效。登录页面包含一个基于 React 的密码强度指示器，用于演示 Fitcloak 的 Vite 代理除了处理 SCSS 外还能处理 JSX/React。

## 4. 使用自己的主题

将 `config.json` 中的 `serverConfig.theme` 指向你的主题目录：

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
