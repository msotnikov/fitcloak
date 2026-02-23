---
title: Inicio Rapido
nav_exclude: true
lang: pt
permalink: /pt/quick-start.html
---

# Início Rápido

## 1. Clonar e configurar

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Baixar temas base do Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Última versão (branch main)
./setup-keycloak-themes.sh archive/release/23.0   # Ou uma versão específica
```

O script baixa os templates FreeMarker do Keycloak e os recursos CSS do PatternFly do mesmo commit, garantindo que as versões sempre correspondam.

## 3. Experimentar a demo

O projeto inclui um tema de demonstração com integração Vite/SCSS e um widget React de força de senha:

```bash
# Instalar dependências da demo
cd demo && npm install && cd ..

# Editar config.json e definir:
# "theme": "demo"
# "devResourcesUrl": "http://localhost:5173/"

# Iniciar servidor de desenvolvimento Vite (em um terminal)
cd demo && npm run dev

# Iniciar Fitcloak (em outro terminal)
./gradlew run
```

Abra [http://localhost:3030](http://localhost:3030). Edite `demo/src/theme.scss` e atualize -- as alterações aparecem instantaneamente via Vite. A página de login inclui um indicador de força de senha com React para demonstrar que o proxy Vite do Fitcloak lida com JSX/React além de SCSS.

## 4. Use seu próprio tema

Aponte `serverConfig.theme` em `config.json` para o diretório do seu tema:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```
