---
title: "快速开始"
nav_exclude: true
lang: zh
permalink: /zh/quick-start.html
---

# 快速开始

## 什么是 Fitcloak

Fitcloak 是一个用于开发 Keycloak 主题的**本地预览服务器**。它**独立于 Keycloak 运行**，不需要运行 Keycloak 本身。

Fitcloak 的功能：

- 使用与 Keycloak 相同的继承链（`Base → Parent → Child`）组装 FreeMarker 模板（`.ftl`）
- 用测试数据替代真实的 Keycloak 数据（realm、用户、客户端等）
- 加载 `theme.properties` 中指定的样式和脚本
- 在 `http://localhost:3030` 将结果提供给浏览器

主题准备好后 — 打包并安装到真实的 Keycloak 实例中。

## 环境要求

- **Java 17+** — 唯一必需的依赖。Gradle 通过 wrapper（`gradlew`）自动下载。
- Node.js 对于基本主题开发**不是必需的**。只有在使用 Vite、Webpack 等打包工具时才需要。

## 步骤 1：克隆并配置

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 步骤 2：下载 Keycloak 基础主题

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # 最新版本（main 分支）
./setup-keycloak-themes.sh archive/release/23.0   # 或指定版本
```

该脚本从同一个提交中下载 Keycloak 的 FreeMarker 模板和 PatternFly CSS，因此版本始终匹配。

## 步骤 3：运行

```bash
./gradlew run
```

打开 [http://localhost:3030](http://localhost:3030) — 你将看到包含所有可用模板列表的仪表盘。这就是 Fitcloak — 一个无需 Keycloak 即可渲染主题的本地服务器。

## 步骤 4：使用自己的主题

将 `config.json` 中的 `serverConfig.theme` 指向你的主题目录：

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

主题目录应遵循标准的 Keycloak 主题结构：

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # 覆盖登录页面
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

## 步骤 5：从打包工具直接连接资源（可选）

编辑 config.json 并设置：
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# 启动 Vite 开发服务器（终端 1）
npm run dev

# 启动 Fitcloak（终端 2）
./gradlew run
```

## 步骤 6：打包并安装主题到 Keycloak

主题准备好后，部署到 Keycloak：

1. 将主题目录复制到 `<KEYCLOAK_HOME>/themes/`：

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. 重启 Keycloak（如果使用 Docker 则重新构建）。

3. 在 Keycloak 管理控制台中，进入 **Realm Settings → Themes** 并选择你的主题。

如果使用了 Vite/Webpack，请先构建前端资源（`npm run build`），确保编译后的文件在主题的 `resources/` 目录中。

## 运行演示主题

项目包含一个带有 Vite/SCSS 集成和 React 密码强度组件的演示主题。演示使用 Vite，因此**需要 Node.js**。

```bash
# 安装演示依赖
cd demo && npm install && cd ..

# 编辑 config.json 并设置：
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# 启动 Vite 开发服务器（终端 1）
cd demo && npm run dev

# 启动 Fitcloak（终端 2）
./gradlew run
```

打开 [http://localhost:3030](http://localhost:3030)。编辑 `demo/src/theme.scss` — 更改通过 Vite HMR 即时生效。
