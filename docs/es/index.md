---
title: Inicio
nav_exclude: true
lang: es
layout: home
permalink: /es/
---

# Fitcloak

Un servidor de vista previa local y ligero para desarrollar **temas nativos de Keycloak con FreeMarker** — sin Docker, base de datos ni ejecutar una instancia completa de Keycloak.

[Comenzar](./quick-start){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[GitHub](https://github.com/msotnikov/fitcloak){: .btn .fs-5 .mb-4 .mb-md-0 .mr-2 }
[Apoyar en Patreon](https://www.patreon.com/msotnikov/gift){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## Como funciona

Fitcloak es un servidor local que **funciona por separado de Keycloak**. Solo se necesita Java 17+ para ejecutarlo.

1. Creas o editas un tema -- plantillas `.ftl` estandar, CSS, JS
2. Fitcloak ensambla las plantillas siguiendo la misma cadena de herencia que Keycloak (`Base → Parent → Child`)
3. Sustituye datos de prueba y sirve la pagina renderizada en tu navegador
4. Cuando el tema esta listo -- copialo a Keycloak y seleccionalo en la consola de administracion
5. Opcionalmente, usa bundlers (Vite, Webpack, etc.) para HMR (Hot Module Reload) para un desarrollo mas rapido con cualquier framework web

## ¿Por qué Fitcloak?

Personalizar las páginas de inicio de sesión, cuenta o correo de Keycloak normalmente implica un ciclo de retroalimentación tedioso: reconstruir el JAR, reiniciar Keycloak, limpiar cachés, actualizar. Fitcloak elimina todo eso — simplemente guarda tu archivo y ve el resultado.

**Toma cualquier plantilla de Keycloak, apunta Fitcloak hacia ella y empieza a trabajar.** El proxy del servidor de desarrollo integrado permite usar cualquier cadena de herramientas frontend — Vite, Webpack, Parcel — con cualquier framework o preprocesador: React, Vue, Svelte, SCSS, Tailwind, lo que prefieras. FreeMarker renderiza la estructura de la página, tus herramientas manejan el frontend, y HMR mantiene el ciclo de retroalimentación instantáneo.

Esto te da toda la flexibilidad del desarrollo frontend moderno mientras permaneces dentro del sistema de temas nativo de Keycloak: sin SPIs personalizados, sin dependencia de proveedor — solo plantillas `.ftl` estándar que se despliegan en cualquier instancia de Keycloak tal cual.


## Características

- **Retroalimentación instantánea** — edita `.ftl` / `.css` / `.properties`, actualiza el navegador
- **Herencia completa** — emula la cadena de temas `Base -> Parent -> Child` de Keycloak
- **Integración Vite/HMR** — proxy a un servidor de desarrollo para reemplazo de módulos en caliente
- **Pruebas dinámicas** — sobrescribe cualquier variable de plantilla mediante parámetros de consulta URL
- **Panel de control** — explorador de plantillas con visualización de herencia y enlaces de QA
- **Sin infraestructura** — solo Java y Gradle, nada más

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Enfoque** | Plantillas FreeMarker nativas + cualquier herramienta frontend | Componentes React compilados en temas |
| **Caso de uso** | Personalizar temas estándar de Keycloak con DX moderna | Construir interfaces completamente nuevas con React |
| **Frontend** | Cualquier framework (React, Vue, Svelte, Alpine.js, vanilla) o ninguno | Solo React/TypeScript |
| **Curva de aprendizaje** | Conoces FreeMarker = listo para empezar | Requiere conocimiento de React/TypeScript |
| **Resultado** | Directorio de tema estándar (funciona en cualquier Keycloak) | JAR con aplicación React compilada |

Keycloakify toma un camino diferente: reemplaza FreeMarker completamente con una SPA de React y tiene su propio pipeline de compilación. Fitcloak trabaja con el sistema de temas estándar de Keycloak — las mismas plantillas `.ftl`, el mismo despliegue, solo un flujo de trabajo de desarrollo mucho mejor.

### Aspecto
![Panel de control de Fitcloak](../assets/images/screenshot-dashboard.png)
![Página de registro de Fitcloak](../assets/images/screenshot-register.png)
