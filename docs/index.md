---
title: Home
layout: home
nav_order: 1
---

# Fitcloak

A lightweight local preview server for developing **native Keycloak FreeMarker themes** — without Docker, database, or running a full Keycloak instance.
{: .fs-6 .fw-300 }

[Get Started]({% link quick-start.md %}){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[View on GitHub](https://github.com/msotnikov/fitcloak){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## Why Fitcloak?

Customizing Keycloak's login/account/email pages normally means a painful feedback loop: rebuild the JAR, restart Keycloak, clear caches, refresh. Fitcloak eliminates all of that — just save your file and see the result.

**Take any Keycloak template, point Fitcloak at it, and start hacking.** The built-in dev server proxy means you can use any frontend toolchain — Vite, Webpack, Parcel — with any framework or preprocessor: React, Vue, Svelte, SCSS, Tailwind, whatever you prefer. FreeMarker renders the page structure, your tools handle the frontend, and HMR keeps the feedback loop instant.

This gives you the full flexibility of modern frontend development while staying within Keycloak's native theming system: no custom SPIs, no vendor lock-in — just standard `.ftl` templates that deploy to any Keycloak instance as-is.

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Approach** | Native FreeMarker templates + any frontend tooling | React components compiled to themes |
| **Use case** | Customizing standard Keycloak themes with modern DX | Building entirely new UIs with React |
| **Frontend** | Any framework (React, Vue, Svelte, Alpine.js, vanilla) or none | React/TypeScript only |
| **Learning curve** | Know FreeMarker = ready to go | Requires React/TypeScript knowledge |
| **Output** | Standard theme directory (works on any Keycloak) | JAR with compiled React app |

Keycloakify takes a different path: it replaces FreeMarker entirely with a React SPA and has its own build pipeline. Fitcloak works with the standard Keycloak theming system — the same `.ftl` templates, the same deployment, just a much better development workflow.

## Features

- **Instant feedback** — edit `.ftl` / `.css` / `.properties`, refresh browser
- **Full inheritance** — emulates Keycloak's `Base -> Parent -> Child` theme chain
- **Vite/HMR integration** — proxy to a dev server for hot module replacement
- **Dynamic testing** — override any template variable via URL query parameters
- **Dashboard** — template browser with inheritance visualization and QA links
- **Zero infrastructure** — just Java and Gradle, nothing else

![Fitcloak Dashboard](assets/images/screenshot-dashboard.png)
![Fitcloak Register Page](assets/images/screenshot-register.png)
