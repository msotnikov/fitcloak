---
title: "테마 개발"
nav_exclude: true
lang: ko
permalink: /ko/theme-development.html
---

# 테마 개발

## 테마 구조

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # 로그인 페이지 오버라이드
    register.ftl              # 등록 페이지 오버라이드
    resources/
      css/styles.css
      js/script.js
      img/logo.png
    messages/
      messages_en.properties
      messages_ru.properties
  mock-data.json              # 테마 전용 테스트 데이터
```

## 템플릿 헬퍼

Fitcloak은 Keycloak의 FreeMarker 객체에 대한 모의 구현을 제공합니다:

| 헬퍼 | 동작 |
|------|------|
| `${msg("key")}` | `.properties` 파일의 지역화된 메시지 |
| `${advancedMsg("key")}` | `msg`와 동일 — 키를 폴백으로 사용하는 메시지 조회 |
| `${kcSanitize(value)}` | 값을 그대로 반환 (모의) |
| `messagesPerField.existsError('field')` | `false` 반환 |
| `messagesPerField.get('field')` | `""` 반환 |
| `auth.showUsername()` | `true` 반환 |
| `auth.showResetCredentials()` | `true` 반환 |

## Keycloak에 패키징 및 설치

Fitcloak은 개발 도구입니다. 테마가 준비되면 Keycloak에 배포합니다:

1. Vite/Webpack을 사용한 경우 — 프론트엔드 리소스를 빌드합니다:
   ```bash
   npm run build
   ```
   컴파일된 파일이 테마의 `resources/` 디렉토리에 있는지 확인하세요.

2. 테마 디렉토리를 Keycloak에 복사합니다:
   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

3. Keycloak을 재시작합니다.

4. 관리 콘솔에서 **Realm Settings → Themes**로 이동하여 테마를 선택합니다.

## Keycloak 테마 레퍼런스

Keycloak의 테마 시스템에 대한 포괄적인 문서는 공식 가이드를 참조하세요:
[Keycloak 테마 개발](https://www.keycloak.org/docs/latest/server_development/#_themes)
