---
title: "설정"
nav_exclude: true
lang: ko
permalink: /ko/configuration.html
---

# 설정

모든 옵션은 [`config.example.json`](https://github.com/msotnikov/fitcloak/blob/main/config.example.json)을 참조하세요.

## 주요 설정

| 필드 | 설명 |
|------|------|
| `serverConfig.theme` | 테마 디렉토리 경로 |
| `serverConfig.port` | 서버 포트 |
| `serverConfig.keycloakThemesPath` | 다운로드된 Keycloak 기본 테마 경로 |
| `serverConfig.devResourcesUrl` | HMR용 Vite/Webpack 개발 서버 URL |
| `serverConfig.qaRealms` | 대시보드에 표시되는 빠른 액세스 링크 |

## 모의 데이터

Keycloak이 일반적으로 템플릿에 전달하는 데이터는 JSON으로 제공됩니다:

- **전역**: `config.json` (`realm`, `url`, `locale` 등의 루트 레벨 필드)
- **테마별**: `<theme-dir>/mock-data.json` (전역 설정 오버라이드)
- **요청별**: URL 쿼리 파라미터 (최고 우선순위)

예시: `http://localhost:3030/login?realm.name=MyRealm&message.summary=Error&message.type=error`

## URL 라우팅

| URL 패턴 | 테마 유형 |
|-----------|-----------|
| `/*` (기본) | Login |
| `/account/*` | Account |
| `/email/*` | Email |
| `/admin/*` | Admin |

`.ftl` 확장자는 선택 사항입니다: `/login`과 `/login.ftl` 모두 동작합니다.
