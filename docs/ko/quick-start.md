---
title: "빠른 시작"
nav_exclude: true
lang: ko
permalink: /ko/quick-start/
---

# 빠른 시작

## 1. 클론 및 설정

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak 기본 테마 다운로드

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # 최신 버전 (main 브랜치)
./setup-keycloak-themes.sh archive/release/23.0   # 또는 특정 버전
```

이 스크립트는 동일한 커밋에서 Keycloak의 FreeMarker 템플릿과 PatternFly CSS 에셋을 다운로드하므로 버전이 항상 일치합니다.

## 3. 데모 체험

프로젝트에는 Vite/SCSS 통합과 React 비밀번호 강도 위젯이 포함된 데모 테마가 있습니다:

```bash
# 데모 의존성 설치
cd demo && npm install && cd ..

# config.json을 편집하여 설정:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Vite 개발 서버 시작 (첫 번째 터미널에서)
cd demo && npm run dev

# Fitcloak 시작 (두 번째 터미널에서)
./gradlew run
```

[http://localhost:3030](http://localhost:3030)을 엽니다. `demo/src/theme.scss`를 편집하고 새로고침하면 Vite를 통해 변경 사항이 즉시 반영됩니다. 로그인 페이지에는 React 기반 비밀번호 강도 표시기가 포함되어 있어, Fitcloak의 Vite 프록시가 SCSS뿐만 아니라 JSX/React도 처리할 수 있음을 보여줍니다.

## 4. 자신의 테마 사용

`config.json`의 `serverConfig.theme`를 자신의 테마 디렉토리로 지정합니다:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
