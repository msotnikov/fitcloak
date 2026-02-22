---
title: "Vite / HMR 통합"
nav_exclude: true
lang: ko
permalink: /ko/vite-integration/
---

# Vite / HMR 통합

포함된 `demo/` 디렉토리는 SCSS와 React를 사용한 Vite 통합의 실용적인 예제입니다.

## 작동 방식

1. 설정에서 `devResourcesUrl` 지정: `"http://localhost:5173/"`
2. `theme.properties`에서 소스 파일을 직접 참조: `styles=css/login.css src/theme.scss`
3. Fitcloak이 `/resources/*`, `/src/*`, `/@*`, `/node_modules/*`를 Vite 개발 서버로 프록시
4. Vite가 SCSS, JSX, TypeScript(또는 기타 전처리기)를 즉석에서 컴파일
5. 개발 서버를 사용할 수 없는 경우 로컬 파일로 폴백

## 자신의 테마에서 사용

테마 디렉토리에 SCSS/PostCSS 등을 포함한 Vite 프로젝트를 설정하고, `theme.properties`에서 소스 파일을 참조하고, `devResourcesUrl`을 Vite로 지정합니다.

### 예제: SCSS 추가

```bash
cd your-theme
npm init -y
npm install --save-dev vite sass
```

`vite.config.js` 생성:

```js
import { defineConfig } from 'vite'

export default defineConfig({
  root: '.',
})
```

`login/theme.properties`에서 SCSS 참조:

```properties
parent=keycloak
styles=css/login.css src/theme.scss
```

### 예제: React 추가

데모 테마가 이 패턴을 보여줍니다 — 로그인 페이지를 강화하는 React 컴포넌트에 대해서는 `demo/src/PasswordStrength.jsx`를 참조하세요. 핵심은 표준 `properties.scripts` 메커니즘 대신 커스텀 `.ftl` 템플릿에서 `<script type="module">`을 사용하는 것입니다.
