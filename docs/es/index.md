---
title: Inicio
nav_exclude: true
lang: es
permalink: /es/
---

# Fitcloak

Un servidor de vista previa local y ligero para desarrollar **temas nativos de Keycloak con FreeMarker** -- sin Docker, base de datos ni ejecutar una instancia completa de Keycloak.

[Comenzar](../quick-start/) [Ver en GitHub](https://github.com/msotnikov/fitcloak)

---

## Por que Fitcloak?

Personalizar las paginas de inicio de sesion, cuenta o correo de Keycloak normalmente implica un ciclo de retroalimentacion tedioso: reconstruir el JAR, reiniciar Keycloak, limpiar caches, actualizar. Fitcloak elimina todo eso -- simplemente guarda tu archivo y ve el resultado.

**Toma cualquier plantilla de Keycloak, apunta Fitcloak hacia ella y empieza a trabajar.** El proxy del servidor de desarrollo integrado permite usar cualquier cadena de herramientas frontend -- Vite, Webpack, Parcel -- con cualquier framework o preprocesador: React, Vue, Svelte, SCSS, Tailwind, lo que prefieras. FreeMarker renderiza la estructura de la pagina, tus herramientas manejan el frontend, y HMR mantiene el ciclo de retroalimentacion instantaneo.

Esto te da toda la flexibilidad del desarrollo frontend moderno mientras permaneces dentro del sistema de temas nativo de Keycloak: sin SPIs personalizados, sin dependencia de proveedor -- solo plantillas `.ftl` estandar que se despliegan en cualquier instancia de Keycloak tal cual.

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Enfoque** | Plantillas FreeMarker nativas + cualquier herramienta frontend | Componentes React compilados en temas |
| **Caso de uso** | Personalizar temas estandar de Keycloak con DX moderna | Construir interfaces completamente nuevas con React |
| **Frontend** | Cualquier framework (React, Vue, Svelte, Alpine.js, vanilla) o ninguno | Solo React/TypeScript |
| **Curva de aprendizaje** | Conoces FreeMarker = listo para empezar | Requiere conocimiento de React/TypeScript |
| **Resultado** | Directorio de tema estandar (funciona en cualquier Keycloak) | JAR con aplicacion React compilada |

Keycloakify toma un camino diferente: reemplaza FreeMarker completamente con una SPA de React y tiene su propio pipeline de compilacion. Fitcloak trabaja con el sistema de temas estandar de Keycloak -- las mismas plantillas `.ftl`, el mismo despliegue, solo un flujo de trabajo de desarrollo mucho mejor.

## Caracteristicas

- **Retroalimentacion instantanea** -- edita `.ftl` / `.css` / `.properties`, actualiza el navegador
- **Herencia completa** -- emula la cadena de temas `Base -> Parent -> Child` de Keycloak
- **Integracion Vite/HMR** -- proxy a un servidor de desarrollo para reemplazo de modulos en caliente
- **Pruebas dinamicas** -- sobrescribe cualquier variable de plantilla mediante parametros de consulta URL
- **Panel de control** -- explorador de plantillas con visualizacion de herencia y enlaces de QA
- **Sin infraestructura** -- solo Java y Gradle, nada mas

![Panel de control de Fitcloak](../assets/images/screenshot-dashboard.png)
![Pagina de registro de Fitcloak](../assets/images/screenshot-register.png)
