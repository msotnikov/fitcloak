---
title: Inicio Rapido
nav_exclude: true
lang: es
permalink: /es/quick-start.html
---

# Inicio Rapido

## Que es Fitcloak

Fitcloak es un **servidor de vista previa local** para el desarrollo de temas de Keycloak. Funciona **por separado de Keycloak** y no requiere ejecutar el propio Keycloak.

Que hace Fitcloak:

- Ensambla plantillas FreeMarker (`.ftl`) siguiendo la misma cadena de herencia que Keycloak (`Base → Parent → Child`)
- Sustituye datos de prueba en lugar de datos reales de Keycloak (realm, user, client, etc.)
- Carga estilos y scripts especificados en `theme.properties`
- Sirve el resultado en el navegador en `http://localhost:3030`

Cuando el tema este listo -- lo empaquetas y lo instalas en el Keycloak real.

## Requisitos

- **Java 17+** -- la unica dependencia obligatoria. Gradle se descarga automaticamente a traves del wrapper (`gradlew`).
- Node.js **no es necesario** para el desarrollo basico de temas. Solo sera necesario si deseas usar bundlers como Vite, Webpack, etc. (ver [Integracion Vite / HMR](./vite-integration)).

## 1. Clonar y configurar

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Descargar temas base de Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Ultima version (rama main)
./setup-keycloak-themes.sh archive/release/23.0   # O una version especifica
```

El script descarga las plantillas FreeMarker de Keycloak y los recursos CSS de PatternFly del mismo commit, por lo que las versiones siempre coinciden.

## 3. Ejecutar

```bash
./gradlew run
```

Abre [http://localhost:3030](http://localhost:3030) -- veras el panel de control con la lista de todas las plantillas disponibles. Esto es Fitcloak -- un servidor local que renderiza tus temas sin Keycloak.

## 4. Usa tu propio tema

Apunta `serverConfig.theme` en `config.json` al directorio de tu tema:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

La estructura del directorio del tema debe seguir la estructura estandar de temas de Keycloak:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Sobrescribir pagina de inicio de sesion
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Para mas informacion sobre la estructura y el desarrollo de temas, consulta [Desarrollo de Temas](./theme-development).

## 5. Conecta recursos directamente desde el bundler (opcional)

Edita config.json y establece:
`"devResourcesUrl": "http://localhost:5173/"`


```bash
# Inicia el servidor de desarrollo Vite (en una terminal)
npm run dev

# Inicia Fitcloak (en otra terminal)
./gradlew run
```
## 6. Empaquetado e instalacion del tema en Keycloak

Cuando el tema este listo, transferelo a Keycloak:

1. Copia el directorio del tema en `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Reinicia Keycloak (o reconstruye si usas Docker).

3. En la consola de administracion de Keycloak, ve a **Realm Settings → Themes** y selecciona tu tema.

Si usaste Vite/Webpack -- primero compila los recursos frontend (`npm run build`) y asegurate de que los archivos compilados esten en el directorio `resources/` de tu tema.

## Ejecutar el tema de demo

El proyecto incluye un tema de demo con integracion Vite/SCSS y un widget React de fortaleza de contrasena. Para la demo se **necesita Node.js**, ya que se utiliza Vite.

```bash
# Instalar dependencias de la demo
cd demo && npm install && cd ..

# Editar config.json:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Iniciar servidor de desarrollo Vite (terminal 1)
cd demo && npm run dev

# Iniciar Fitcloak (terminal 2)
./gradlew run
```

Abre [http://localhost:3030](http://localhost:3030). Edita `demo/src/theme.scss` -- los cambios se reflejan instantaneamente a traves del HMR de Vite.
