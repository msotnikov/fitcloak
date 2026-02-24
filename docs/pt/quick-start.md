---
title: Inicio Rapido
nav_exclude: true
lang: pt
permalink: /pt/quick-start.html
---

# Inicio Rapido

## O que e Fitcloak

Fitcloak e um **servidor de pre-visualizacao local** para o desenvolvimento de temas Keycloak. Ele funciona **separadamente do Keycloak** e nao requer a execucao do proprio Keycloak.

O que o Fitcloak faz:

- Monta templates FreeMarker (`.ftl`) seguindo a mesma cadeia de heranca do Keycloak (`Base → Parent → Child`)
- Substitui dados de teste no lugar de dados reais do Keycloak (realm, user, client, etc.)
- Carrega estilos e scripts especificados em `theme.properties`
- Serve o resultado no navegador em `http://localhost:3030`

Quando o tema estiver pronto -- voce o empacota e instala no Keycloak real.

## Requisitos

- **Java 17+** -- a unica dependencia obrigatoria. O Gradle e baixado automaticamente pelo wrapper (`gradlew`).
- Node.js **nao e necessario** para o desenvolvimento basico de temas. Sera necessario apenas se voce quiser usar bundlers como Vite, Webpack, etc. (veja [Integracao Vite / HMR](./vite-integration)).

## 1. Clonar e configurar

```bash
git clone https://github.com/msotnikov/fitcloak.git
cd fitcloak
cp config.example.json config.json
```

## 2. Baixar temas base do Keycloak

```bash
chmod +x setup-keycloak-themes.sh
./setup-keycloak-themes.sh                        # Ultima versao (branch main)
./setup-keycloak-themes.sh archive/release/23.0   # Ou uma versao especifica
```

O script baixa os templates FreeMarker do Keycloak e os recursos CSS do PatternFly do mesmo commit, garantindo que as versoes sempre correspondam.

## 3. Executar

```bash
./gradlew run
```

Abra [http://localhost:3030](http://localhost:3030) -- voce vera o painel de controle com a lista de todos os templates disponiveis. Isso e o Fitcloak -- um servidor local que renderiza seus temas sem Keycloak.

## 4. Use seu proprio tema

Aponte `serverConfig.theme` em `config.json` para o diretorio do seu tema:

```json
{
  "serverConfig": {
    "theme": "path/to/your-theme",
    "keycloakThemesPath": "keycloak/base/themes/src/main/resources/theme"
  }
}
```

A estrutura do diretorio do tema deve seguir a estrutura padrao de temas do Keycloak:

```
your-theme/
  login/
    theme.properties          # parent=keycloak, styles, scripts
    login.ftl                 # Sobrescrever pagina de login
    resources/
      css/styles.css
    messages/
      messages_en.properties
```

Para mais informacoes sobre a estrutura e o desenvolvimento de temas, consulte [Desenvolvimento de Temas](./theme-development).

## 5. Conecte recursos diretamente do bundler (opcional)

Edite config.json e defina:
`"devResourcesUrl": "http://localhost:5173/"`


```bash
# Inicie o servidor de desenvolvimento Vite (em um terminal)
npm run dev

# Inicie o Fitcloak (em outro terminal)
./gradlew run
```
## 6. Empacotamento e instalacao do tema no Keycloak

Quando o tema estiver pronto, transfira-o para o Keycloak:

1. Copie o diretorio do tema para `<KEYCLOAK_HOME>/themes/`:

   ```bash
   cp -r your-theme /opt/keycloak/themes/your-theme
   ```

2. Reinicie o Keycloak (ou reconstrua se usar Docker).

3. No console de administracao do Keycloak, va em **Realm Settings → Themes** e selecione seu tema.

Se voce usou Vite/Webpack -- primeiro compile os recursos frontend (`npm run build`) e certifique-se de que os arquivos compilados estejam no diretorio `resources/` do seu tema.

## Executar o tema demo

O projeto inclui um tema demo com integracao Vite/SCSS e um widget React de forca de senha. Para a demo e **necessario Node.js**, pois o Vite e utilizado.

```bash
# Instalar dependencias da demo
cd demo && npm install && cd ..

# Editar config.json:
#   "theme": "demo"
#   "devResourcesUrl": "http://localhost:5173/"

# Iniciar servidor de desenvolvimento Vite (terminal 1)
cd demo && npm run dev

# Iniciar Fitcloak (terminal 2)
./gradlew run
```

Abra [http://localhost:3030](http://localhost:3030). Edite `demo/src/theme.scss` -- as alteracoes aparecem instantaneamente atraves do Vite HMR.
