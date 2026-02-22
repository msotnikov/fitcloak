---
title: "홈"
nav_exclude: true
lang: ko
permalink: /ko/
---

# Fitcloak

**네이티브 Keycloak FreeMarker 테마** 개발을 위한 경량 로컬 프리뷰 서버 — Docker, 데이터베이스, 전체 Keycloak 인스턴스 실행이 필요 없습니다.

[시작하기](./quick-start)
[GitHub에서 보기](https://github.com/msotnikov/fitcloak)

---

## 왜 Fitcloak인가?

Keycloak의 로그인/계정/이메일 페이지를 커스터마이징하는 것은 보통 고통스러운 피드백 루프를 의미합니다: JAR 재빌드, Keycloak 재시작, 캐시 삭제, 새로고침. Fitcloak은 이 모든 것을 제거합니다 — 파일을 저장하기만 하면 결과를 확인할 수 있습니다.

**아무 Keycloak 템플릿이나 선택하고, Fitcloak을 지정한 다음, 개발을 시작하세요.** 내장된 개발 서버 프록시 덕분에 어떤 프론트엔드 툴체인이든 사용할 수 있습니다 — Vite, Webpack, Parcel — 어떤 프레임워크나 전처리기와도 함께: React, Vue, Svelte, SCSS, Tailwind 등 원하는 것 무엇이든. FreeMarker가 페이지 구조를 렌더링하고, 여러분의 도구가 프론트엔드를 처리하며, HMR이 즉각적인 피드백을 유지합니다.

이를 통해 Keycloak의 네이티브 테마 시스템을 유지하면서 현대적인 프론트엔드 개발의 완전한 유연성을 얻을 수 있습니다: 커스텀 SPI 불필요, 벤더 종속 없음 — 어떤 Keycloak 인스턴스에든 그대로 배포할 수 있는 표준 `.ftl` 템플릿뿐입니다.

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **접근 방식** | 네이티브 FreeMarker 템플릿 + 모든 프론트엔드 도구 | React 컴포넌트를 테마로 컴파일 |
| **사용 사례** | 현대적인 DX로 표준 Keycloak 테마 커스터마이징 | React로 완전히 새로운 UI 구축 |
| **프론트엔드** | 모든 프레임워크(React, Vue, Svelte, Alpine.js, 바닐라 JS) 또는 프레임워크 없이 | React/TypeScript만 |
| **학습 곡선** | FreeMarker를 알면 바로 시작 가능 | React/TypeScript 지식 필요 |
| **출력** | 표준 테마 디렉토리(모든 Keycloak에서 작동) | 컴파일된 React 앱을 포함한 JAR |

Keycloakify는 다른 길을 택합니다: FreeMarker를 React SPA로 완전히 대체하고 자체 빌드 파이프라인을 갖추고 있습니다. Fitcloak은 표준 Keycloak 테마 시스템에서 작동합니다 — 동일한 `.ftl` 템플릿, 동일한 배포 방식, 단지 훨씬 더 나은 개발 워크플로우입니다.

## 기능

- **즉각적인 피드백** — `.ftl` / `.css` / `.properties`를 편집하고 브라우저 새로고침
- **완전한 상속** — Keycloak의 `Base -> Parent -> Child` 테마 체인 에뮬레이션
- **Vite/HMR 통합** — 핫 모듈 교체를 위한 개발 서버 프록시
- **동적 테스트** — URL 쿼리 파라미터로 모든 템플릿 변수 오버라이드
- **대시보드** — 상속 시각화 및 QA 링크가 포함된 템플릿 브라우저
- **인프라 불필요** — Java와 Gradle만 있으면 됩니다

![Fitcloak 대시보드](../assets/images/screenshot-dashboard.png)
![Fitcloak 등록 페이지](../assets/images/screenshot-register.png)
