---
title: "Startseite"
nav_exclude: true
lang: de
permalink: /de/
---

# Fitcloak

Ein leichtgewichtiger lokaler Vorschauserver zur Entwicklung **nativer Keycloak-FreeMarker-Themes** -- ohne Docker, Datenbank oder laufende Keycloak-Instanz.

[Erste Schritte](./quick-start)
[Auf GitHub ansehen](https://github.com/msotnikov/fitcloak)

---

## Warum Fitcloak?

Die Anpassung der Login-/Konto-/E-Mail-Seiten von Keycloak bedeutet normalerweise einen mühsamen Feedback-Zyklus: JAR neu bauen, Keycloak neu starten, Caches leeren, Seite aktualisieren. Fitcloak beseitigt all das -- speichern Sie einfach Ihre Datei und sehen Sie das Ergebnis.

**Nehmen Sie ein beliebiges Keycloak-Template, richten Sie Fitcloak darauf und legen Sie los.** Der integrierte Dev-Server-Proxy ermöglicht die Nutzung jeder Frontend-Toolchain -- Vite, Webpack, Parcel -- mit jedem Framework oder Präprozessor: React, Vue, Svelte, SCSS, Tailwind, was immer Sie bevorzugen. FreeMarker rendert die Seitenstruktur, Ihre Tools übernehmen das Frontend, und HMR sorgt für sofortiges Feedback.

Das gibt Ihnen die volle Flexibilität moderner Frontend-Entwicklung und bleibt dabei innerhalb des nativen Keycloak-Theming-Systems: keine benutzerdefinierten SPIs, kein Vendor-Lock-in -- nur Standard-`.ftl`-Templates, die auf jeder Keycloak-Instanz direkt deployt werden können.

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Ansatz** | Native FreeMarker-Templates + beliebiges Frontend-Tooling | React-Komponenten, die zu Themes kompiliert werden |
| **Anwendungsfall** | Anpassung von Standard-Keycloak-Themes mit moderner DX | Erstellen komplett neuer UIs mit React |
| **Frontend** | Jedes Framework (React, Vue, Svelte, Alpine.js, Vanilla) oder keines | Nur React/TypeScript |
| **Lernkurve** | FreeMarker kennen = startklar | Erfordert React/TypeScript-Kenntnisse |
| **Ergebnis** | Standard-Theme-Verzeichnis (funktioniert auf jedem Keycloak) | JAR mit kompilierter React-App |

Keycloakify geht einen anderen Weg: Es ersetzt FreeMarker vollständig durch eine React-SPA und hat eine eigene Build-Pipeline. Fitcloak arbeitet mit dem Standard-Keycloak-Theming-System -- dieselben `.ftl`-Templates, dasselbe Deployment, nur ein deutlich besserer Entwicklungs-Workflow.

## Funktionen

- **Sofortiges Feedback** -- `.ftl` / `.css` / `.properties` bearbeiten, Browser aktualisieren
- **Vollständige Vererbung** -- emuliert Keycloaks `Base -> Parent -> Child`-Theme-Kette
- **Vite/HMR-Integration** -- Proxy zu einem Dev-Server für Hot Module Replacement
- **Dynamisches Testen** -- jede Template-Variable über URL-Query-Parameter überschreiben
- **Dashboard** -- Template-Browser mit Vererbungsvisualisierung und QA-Links
- **Keine Infrastruktur nötig** -- nur Java und Gradle, sonst nichts

![Fitcloak Dashboard](../assets/images/screenshot-dashboard.png)
![Fitcloak Registrierungsseite](../assets/images/screenshot-register.png)
