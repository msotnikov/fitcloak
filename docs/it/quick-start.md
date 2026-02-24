---
title: Guida Rapida
nav_exclude: true
lang: it
permalink: /it/quick-start.html
---

# Guida Rapida

## Cos'e Fitcloak

Fitcloak e un **server di anteprima locale** per lo sviluppo di temi Keycloak. Funziona **separatamente da Keycloak** e non richiede l'esecuzione di Keycloak stesso.

Cosa fa Fitcloak:

- Assembla i template FreeMarker (`.ftl`) seguendo la stessa catena di ereditarieta di Keycloak (`Base → Parent → Child`)
- Sostituisce i dati di test al posto dei dati reali di Keycloak (realm, user, client, ecc.)
- Carica stili e script specificati in `theme.properties`
- Serve il risultato nel browser su `http://localhost:3030`

Quando il tema e pronto -- lo impacchetti e lo installi nel Keycloak reale.

## Requisiti

- **Java 17+** -- l'unica dipendenza obbligatoria. Gradle si scarica automaticamente tramite il wrapper (`gradlew`).
- Node.js **non e necessario** per lo sviluppo base dei temi. Sara richiesto solo se vuoi usare bundler come Vite, Webpack, ecc. (vedi [Integrazione Vite / HMR](./vite-integration)).

## 1. Clona e configura

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Scarica i temi base di Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Ultima versione (branch main)
./setup-keycloak-themes.sh archive/release/23.0   # Oppure una versione specifica
```

Lo script scarica i template FreeMarker di Keycloak e le risorse CSS di PatternFly dallo stesso commit, cosi le versioni corrispondono sempre.

## 3. Avvio

```bash
./gradlew run
```

Apri [http://localhost:3030](http://localhost:3030) -- vedrai la dashboard con l'elenco di tutti i template disponibili. Questo e Fitcloak -- un server locale che renderizza i tuoi temi senza Keycloak.

## 4. Usa il tuo tema

Punta `serverConfig.theme` in `config.json` alla directory del tuo tema:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

La struttura della directory del tema deve seguire la struttura standard dei temi Keycloak:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Sovrascrivere la pagina di login
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Per maggiori informazioni sulla struttura e lo sviluppo dei temi, consulta [Sviluppo Temi](./theme-development).

## 5. Collega le risorse direttamente dal bundler (opzionale)

Modifica config.json e imposta:
`"devResourcesUrl": "http://localhost:5173/"`


```bash
# Avvia il server di sviluppo Vite (in un terminale)
npm run dev

# Avvia Fitcloak (in un altro terminale)
./gradlew run
```
## 6. Pacchettizzazione e installazione del tema in Keycloak

Quando il tema e pronto, trasferiscilo in Keycloak:

1. Copia la directory del tema in `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Riavvia Keycloak (o ricompila se usi Docker).

3. Nella console di amministrazione di Keycloak, vai su **Realm Settings → Themes** e seleziona il tuo tema.

Se hai usato Vite/Webpack -- prima compila le risorse frontend (`npm run build`) e assicurati che i file compilati si trovino nella directory `resources/` del tuo tema.

## Avvio del tema demo

Il progetto include un tema demo con integrazione Vite/SCSS e un widget React per la robustezza della password. Per la demo e **necessario Node.js**, poiche viene utilizzato Vite.

```bash
# Installa le dipendenze della demo
cd demo && npm install && cd ..

# Modifica config.json:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Avvia il server di sviluppo Vite (terminale 1)
cd demo && npm run dev

# Avvia Fitcloak (terminale 2)
./gradlew run
```

Apri [http://localhost:3030](http://localhost:3030). Modifica `demo/src/theme.scss` -- le modifiche appaiono istantaneamente tramite Vite HMR.
