---
title: "문제 해결"
nav_exclude: true
lang: ko
permalink: /ko/troubleshooting.html
---

# 문제 해결

## "has evaluated to null or missing" 오류

```
Template error in login.ftl: The following has evaluated to null or missing:
==> realm.password  [in template "login.ftl" at line 2, column 109]
```

이는 FreeMarker 템플릿이 모의 데이터에 존재하지 않는 변수를 참조하고 있다는 의미입니다. 실제 Keycloak 인스턴스에서는 이러한 변수가 서버에 의해 채워지지만, Fitcloak에서는 JSON을 통해 제공해야 합니다.

### 해결 방법 1: 누락된 변수를 모의 데이터에 추가

테마의 `mock-data.json`(또는 `config.json`)을 열고 누락된 필드를 추가합니다:

```json
{
  "realm": {
    "name": "my-realm",
    "password": true
  }
}
```

Keycloak 템플릿이 참조하는 일반적인 `realm` 필드:

| 필드 | 타입 | 일반적인 값 | 사용처 |
|------|------|-------------|--------|
| `password` | boolean | `true` | `login.ftl` — 비밀번호 폼 표시 여부 제어 |
| `registrationAllowed` | boolean | `true` | `login.ftl` — "등록" 링크 |
| `resetPasswordAllowed` | boolean | `true` | `login.ftl` — "비밀번호 찾기" 링크 |
| `rememberMe` | boolean | `true` | `login.ftl` — "로그인 상태 유지" 체크박스 |
| `loginWithEmailAllowed` | boolean | `true` | `login.ftl` — 사용자명 필드 라벨 |
| `registrationEmailAsUsername` | boolean | `false` | `login.ftl` — 사용자명 필드 라벨 |
| `internationalizationEnabled` | boolean | `false` | `template.ftl` — 언어 선택기 |

### 해결 방법 2: URL 쿼리 파라미터로 요청별 오버라이드

파일을 편집하지 않고 빠르게 테스트할 때 유용합니다:

```
http://localhost:3030/login?realm.password=true&realm.rememberMe=false
```

### 해결 방법 3: 템플릿에서 FreeMarker 기본값 사용

커스텀 `.ftl` 파일을 작성하는 경우, `!` (기본값) 연산자를 사용하여 누락된 값을 방지합니다:

```ftl
<#-- 다음 대신: -->
<#if realm.password>

<#-- 기본값 사용: -->
<#if (realm.password)!true>
```

`!` 연산자는 값이 누락되었을 때 폴백 값을 제공합니다. `(realm.password)!true`는 "`realm.password`가 존재하면 그 값을 사용하고, 그렇지 않으면 `true`를 사용한다"는 의미입니다.

## 템플릿에 필요한 변수 찾기

Keycloak 템플릿은 많은 변수(`realm`, `url`, `auth`, `login`, `social`, `properties` 등)를 참조합니다. 새로운 `.ftl` 파일을 오버라이드하거나 추가할 때 추가 모의 데이터 값을 제공해야 할 수 있습니다.

**접근 방법:** `.ftl` 파일을 살펴보고, 모든 `${...}` 표현식과 `<#if ...>` 조건을 찾은 다음, 참조되는 각 객체가 모의 데이터에 존재하는지 확인합니다. 데모의 [`mock-data.json`](https://github.com/msotnikov/fitcloak/blob/main/demo/mock-data.json)을 복사의 시작점으로 사용하면 좋습니다.
