---
title: "빠른 시작"
nav_exclude: true
lang: ko
permalink: /ko/quick-start.html
---

# 빠른 시작

## Fitcloak이란

Fitcloak은 Keycloak 테마를 개발하기 위한 **로컬 프리뷰 서버**입니다. **Keycloak과 별도로 실행**되며 Keycloak 자체를 실행할 필요가 없습니다.

Fitcloak이 하는 일:

- Keycloak과 동일한 상속 체인(`Base → Parent → Child`)을 사용하여 FreeMarker 템플릿(`.ftl`)을 조립합니다
- 실제 Keycloak 데이터(realm, 사용자, 클라이언트 등) 대신 테스트 데이터를 대입합니다
- `theme.properties`에 지정된 스타일과 스크립트를 로드합니다
- `http://localhost:3030`에서 브라우저에 결과를 제공합니다

테마가 준비되면 — 패키징하여 실제 Keycloak 인스턴스에 설치합니다.

## 요구 사항

- **Java 17+** — 유일한 필수 의존성입니다. Gradle은 래퍼(`gradlew`)를 통해 자동으로 다운로드됩니다.
- Node.js는 기본 테마 개발에는 **필요하지 않습니다**. Vite, Webpack 등의 번들러를 사용할 때만 필요합니다.

## 단계 1: 클론 및 설정

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 단계 2: Keycloak 기본 테마 다운로드

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # 최신 버전 (main 브랜치)
./setup-keycloak-themes.sh archive/release/23.0   # 또는 특정 버전
```

이 스크립트는 동일한 커밋에서 Keycloak의 FreeMarker 템플릿과 PatternFly CSS를 다운로드하므로 버전이 항상 일치합니다.

## 단계 3: 실행

```bash
./gradlew run
```

[http://localhost:3030](http://localhost:3030)을 엽니다 — 사용 가능한 모든 템플릿 목록이 포함된 대시보드가 표시됩니다. 이것이 Fitcloak입니다 — Keycloak 없이 테마를 렌더링하는 로컬 서버입니다.

## 단계 4: 자신의 테마 사용

`config.json`의 `serverConfig.theme`를 자신의 테마 디렉토리로 지정합니다:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

테마 디렉토리는 표준 Keycloak 테마 구조를 따라야 합니다:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # 로그인 페이지 오버라이드
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

## 단계 5: 번들러에서 리소스 직접 연결 (선택 사항)

config.json을 편집하여 다음을 설정합니다:
`"devResourcesUrl": "http://localhost:5173/"`

```bash
# Vite 개발 서버 시작 (터미널 1)
npm run dev

# Fitcloak 시작 (터미널 2)
./gradlew run
```

## 단계 6: 테마를 패키징하여 Keycloak에 설치

테마가 준비되면 Keycloak에 배포합니다:

1. 테마 디렉토리를 `<KEYCLOAK_HOME>/themes/`에 복사합니다:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Keycloak을 재시작합니다 (Docker를 사용하는 경우 재빌드).

3. Keycloak 관리 콘솔에서 **Realm Settings → Themes**로 이동하여 테마를 선택합니다.

Vite/Webpack을 사용한 경우, 먼저 프론트엔드 리소스를 빌드하세요 (`npm run build`). 컴파일된 파일이 테마의 `resources/` 디렉토리에 있는지 확인하세요.

## 데모 테마 실행

프로젝트에는 Vite/SCSS 통합과 React 비밀번호 강도 위젯이 포함된 데모 테마가 있습니다. 데모는 Vite를 사용하므로 **Node.js가 필요**합니다.

```bash
# 데모 의존성 설치
cd demo && npm install && cd ..

# config.json을 편집하여 설정:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Vite 개발 서버 시작 (터미널 1)
cd demo && npm run dev

# Fitcloak 시작 (터미널 2)
./gradlew run
```

[http://localhost:3030](http://localhost:3030)을 엽니다. `demo/src/theme.scss`를 편집하면 — Vite HMR을 통해 변경 사항이 즉시 반영됩니다.
