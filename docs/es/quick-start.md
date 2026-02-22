---
title: Inicio Rapido
nav_exclude: true
lang: es
permalink: /es/quick-start/
---

# Inicio Rapido

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

## 3. Probar la demo

El proyecto incluye un tema de demostracion con integracion Vite/SCSS y un widget React de fortaleza de contrasena:

```bash
# Instalar dependencias de la demo
cd demo && npm install && cd ..

# Editar config.json y establecer:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Iniciar servidor de desarrollo Vite (en una terminal)
cd demo && npm run dev

# Iniciar Fitcloak (en otra terminal)
./gradlew run
```

Abre [http://localhost:3030](http://localhost:3030). Edita `demo/src/theme.scss` y actualiza -- los cambios aparecen instantaneamente a traves de Vite. La pagina de inicio de sesion incluye un indicador de fortaleza de contrasena con React para demostrar que el proxy Vite de Fitcloak maneja JSX/React ademas de SCSS.

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
