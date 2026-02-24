---
title: "Schnellstart"
nav_exclude: true
lang: de
permalink: /de/quick-start.html
---

# Schnellstart

## Was ist Fitcloak

Fitcloak ist ein **lokaler Vorschauserver** fur die Entwicklung von Keycloak-Themes. Er lauft **unabhangig von Keycloak** und erfordert keinen laufenden Keycloak-Server.

Was Fitcloak macht:

- Baut FreeMarker-Templates (`.ftl`) nach derselben Vererbungskette wie Keycloak zusammen (`Base → Parent → Child`)
- Setzt Testdaten anstelle echter Keycloak-Daten ein (Realm, User, Client usw.)
- Ladt Styles und Skripte, die in `theme.properties` angegeben sind
- Liefert das Ergebnis im Browser unter `http://localhost:3030`

Wenn das Theme fertig ist -- verpacken Sie es und installieren es im echten Keycloak.

## Voraussetzungen

- **Java 17+** -- die einzige Pflichtabhangigkeit. Gradle wird automatisch uber den Wrapper (`gradlew`) heruntergeladen.
- Node.js wird **nicht benotigt** fur die grundlegende Theme-Entwicklung. Es wird nur benotigt, wenn Sie Bundler wie Vite, Webpack usw. verwenden mochten (siehe [Vite/HMR-Integration](./vite-integration)).

## 1. Klonen & konfigurieren

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Keycloak-Basis-Themes herunterladen

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Neueste Version (Main-Branch)
./setup-keycloak-themes.sh archive/release/23.0   # Oder eine bestimmte Version
```

Das Skript ladt Keycloaks FreeMarker-Templates und PatternFly-CSS-Assets vom selben Commit herunter, sodass die Versionen immer ubereinstimmen.

## 3. Starten

```bash
./gradlew run
```

Offnen Sie [http://localhost:3030](http://localhost:3030) -- Sie sehen das Dashboard mit einer Liste aller verfugbaren Templates. Das ist Fitcloak -- ein lokaler Server, der Ihre Themes ohne Keycloak rendert.

## 4. Eigenes Theme verwenden

Setzen Sie `serverConfig.theme` in `config.json` auf das Verzeichnis Ihres Themes:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

Die Verzeichnisstruktur des Themes muss der Standard-Keycloak-Theme-Struktur entsprechen:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Login-Seite uberschreiben
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Weitere Informationen zu Struktur und Entwicklung von Themes finden Sie unter [Theme-Entwicklung](./theme-development).

## 5. Ressourcen direkt vom Bundler einbinden (optional)

Bearbeiten Sie config.json und setzen Sie:
`"devResourcesUrl": "http://localhost:5173/"`


```bash
# Vite Dev-Server starten (in einem Terminal)
npm run dev

# Fitcloak starten (in einem anderen Terminal)
./gradlew run
```
## 6. Paketierung und Installation des Themes in Keycloak

Wenn das Theme fertig ist, ubertragen Sie es nach Keycloak:

1. Kopieren Sie das Theme-Verzeichnis in `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Starten Sie Keycloak neu (oder bauen Sie es neu, wenn Sie Docker verwenden).

3. In der Keycloak-Admin-Konsole gehen Sie zu **Realm Settings → Themes** und wahlen Sie Ihr Theme aus.

Wenn Sie Vite/Webpack verwendet haben -- bauen Sie zuerst die Frontend-Ressourcen (`npm run build`) und stellen Sie sicher, dass die kompilierten Dateien im Verzeichnis `resources/` Ihres Themes liegen.

## Demo-Theme starten

Das Projekt enthalt ein Demo-Theme mit Vite/SCSS-Integration und einem React-Passworstarke-Widget. Fur die Demo wird **Node.js benotigt**, da Vite verwendet wird.

```bash
# Demo-Abhangigkeiten installieren
cd demo && npm install && cd ..

# config.json bearbeiten:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Vite Dev-Server starten (Terminal 1)
cd demo && npm run dev

# Fitcloak starten (Terminal 2)
./gradlew run
```

Offnen Sie [http://localhost:3030](http://localhost:3030). Bearbeiten Sie `demo/src/theme.scss` -- Anderungen werden sofort uber Vite HMR angezeigt.
