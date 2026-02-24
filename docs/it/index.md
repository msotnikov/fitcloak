---
title: Home
nav_exclude: true
lang: it
layout: home
permalink: /it/
---

# Fitcloak

Un server di anteprima locale e leggero per sviluppare **temi nativi Keycloak con FreeMarker** -- senza Docker, database o l'esecuzione di un'istanza Keycloak completa.

[Inizia](./quick-start){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[GitHub](https://github.com/msotnikov/fitcloak){: .btn .fs-5 .mb-4 .mb-md-0 .mr-2 }
[Supporta su Patreon](https://www.patreon.com/msotnikov/gift){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## Come funziona

Fitcloak e un server locale che **funziona separatamente da Keycloak**. Per l'avvio e necessario solo Java 17+.

1. Crei o modifichi un tema -- template `.ftl` standard, CSS, JS
2. Fitcloak assembla i template seguendo la stessa catena di ereditarieta di Keycloak (`Base → Parent → Child`)
3. Sostituisce i dati di test e serve la pagina renderizzata nel tuo browser
4. Quando il tema e pronto -- copialo in Keycloak e selezionalo nella console di amministrazione
5. Opzionalmente, usa bundler (Vite, Webpack, ecc.) per l'HMR (Hot Module Reload) per uno sviluppo piu rapido con qualsiasi framework web

## Perché Fitcloak?

Personalizzare le pagine di login/account/email di Keycloak normalmente comporta un ciclo di feedback frustrante: ricompilare il JAR, riavviare Keycloak, svuotare le cache, aggiornare. Fitcloak elimina tutto questo -- basta salvare il file e vedere il risultato.

**Prendi un qualsiasi template Keycloak, punta Fitcloak verso di esso e inizia a lavorare.** Il proxy del server di sviluppo integrato ti permette di usare qualsiasi toolchain frontend -- Vite, Webpack, Parcel -- con qualsiasi framework o preprocessore: React, Vue, Svelte, SCSS, Tailwind, quello che preferisci. FreeMarker renderizza la struttura della pagina, i tuoi strumenti gestiscono il frontend e HMR mantiene il ciclo di feedback istantaneo.

Questo ti offre tutta la flessibilità dello sviluppo frontend moderno restando all'interno del sistema di temi nativo di Keycloak: nessun SPI personalizzato, nessun vendor lock-in -- solo template `.ftl` standard che si distribuiscono su qualsiasi istanza Keycloak così come sono.

## Funzionalità

- **Feedback istantaneo** -- modifica `.ftl` / `.css` / `.properties`, aggiorna il browser
- **Ereditarietà completa** -- emula la catena di temi `Base -> Parent -> Child` di Keycloak
- **Integrazione Vite/HMR** -- proxy verso un server di sviluppo per il hot module replacement
- **Test dinamici** -- sovrascrivi qualsiasi variabile del template tramite parametri di query URL
- **Dashboard** -- browser dei template con visualizzazione dell'ereditarietà e link QA
- **Zero infrastruttura** -- solo Java e Gradle, nient'altro

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Approccio** | Template FreeMarker nativi + qualsiasi strumento frontend | Componenti React compilati in temi |
| **Caso d'uso** | Personalizzare temi Keycloak standard con DX moderna | Costruire interfacce completamente nuove con React |
| **Frontend** | Qualsiasi framework (React, Vue, Svelte, Alpine.js, vanilla) o nessuno | Solo React/TypeScript |
| **Curva di apprendimento** | Conosci FreeMarker = pronto per iniziare | Richiede conoscenza di React/TypeScript |
| **Output** | Directory tema standard (funziona su qualsiasi Keycloak) | JAR con applicazione React compilata |

Keycloakify segue un percorso diverso: sostituisce completamente FreeMarker con una SPA React e ha la propria pipeline di build. Fitcloak lavora con il sistema di temi standard di Keycloak -- gli stessi template `.ftl`, lo stesso deployment, solo un flusso di lavoro di sviluppo molto migliore.


### Aspetto
![Dashboard Fitcloak](../assets/images/screenshot-dashboard.png)
![Pagina di registrazione Fitcloak](../assets/images/screenshot-register.png)
