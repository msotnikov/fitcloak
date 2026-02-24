---
title: Inicio
nav_exclude: true
lang: pt
layout: home
permalink: /pt/
---

# Fitcloak

Um servidor de pré-visualização local e leve para desenvolver **temas nativos Keycloak com FreeMarker** -- sem Docker, banco de dados ou execução de uma instância Keycloak completa.

[Comecar](./quick-start){: .btn .btn-primary .fs-5 .mb-4 .mb-md-0 .mr-2 }
[GitHub](https://github.com/msotnikov/fitcloak){: .btn .fs-5 .mb-4 .mb-md-0 .mr-2 }
[Apoiar no Patreon](https://www.patreon.com/msotnikov/gift){: .btn .fs-5 .mb-4 .mb-md-0 }

---

## Como funciona

Fitcloak e um servidor local que **funciona separadamente do Keycloak**. Apenas Java 17+ e necessario para a execucao.

1. Voce cria ou edita um tema -- templates `.ftl` padrao, CSS, JS
2. Fitcloak monta os templates seguindo a mesma cadeia de heranca do Keycloak (`Base → Parent → Child`)
3. Substitui dados de teste e serve a pagina renderizada no seu navegador
4. Quando o tema esta pronto -- copie-o para o Keycloak e selecione-o no console de administracao
5. Opcionalmente, use bundlers (Vite, Webpack, etc.) para HMR (Hot Module Reload) para um desenvolvimento mais rapido com qualquer framework web

## Por que Fitcloak?

Personalizar as páginas de login/conta/email do Keycloak normalmente significa um ciclo de feedback doloroso: reconstruir o JAR, reiniciar o Keycloak, limpar caches, atualizar. O Fitcloak elimina tudo isso -- basta salvar o arquivo e ver o resultado.

**Pegue qualquer template do Keycloak, aponte o Fitcloak para ele e comece a trabalhar.** O proxy do servidor de desenvolvimento integrado permite usar qualquer cadeia de ferramentas frontend -- Vite, Webpack, Parcel -- com qualquer framework ou pré-processador: React, Vue, Svelte, SCSS, Tailwind, o que você preferir. O FreeMarker renderiza a estrutura da página, suas ferramentas cuidam do frontend, e o HMR mantém o ciclo de feedback instantâneo.

Isso proporciona toda a flexibilidade do desenvolvimento frontend moderno permanecendo dentro do sistema de temas nativo do Keycloak: sem SPIs personalizados, sem dependência de fornecedor -- apenas templates `.ftl` padrão que fazem deploy em qualquer instância Keycloak como estão.

## Funcionalidades

- **Feedback instantâneo** -- edite `.ftl` / `.css` / `.properties`, atualize o navegador
- **Herança completa** -- emula a cadeia de temas `Base -> Parent -> Child` do Keycloak
- **Integração Vite/HMR** -- proxy para um servidor de desenvolvimento para hot module replacement
- **Testes dinâmicos** -- sobrescreva qualquer variável de template via parâmetros de consulta URL
- **Painel de controle** -- navegador de templates com visualização de herança e links de QA
- **Zero infraestrutura** -- apenas Java e Gradle, nada mais


### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Abordagem** | Templates FreeMarker nativos + qualquer ferramenta frontend | Componentes React compilados em temas |
| **Caso de uso** | Personalizar temas Keycloak padrão com DX moderna | Construir interfaces completamente novas com React |
| **Frontend** | Qualquer framework (React, Vue, Svelte, Alpine.js, vanilla) ou nenhum | Apenas React/TypeScript |
| **Curva de aprendizado** | Conhece FreeMarker = pronto para começar | Requer conhecimento de React/TypeScript |
| **Resultado** | Diretório de tema padrão (funciona em qualquer Keycloak) | JAR com aplicação React compilada |

O Keycloakify segue um caminho diferente: substitui completamente o FreeMarker por uma SPA React e tem seu próprio pipeline de build. O Fitcloak trabalha com o sistema de temas padrão do Keycloak -- os mesmos templates `.ftl`, o mesmo deployment, apenas um fluxo de trabalho de desenvolvimento muito melhor.


### Aparencia
![Painel de controle do Fitcloak](../assets/images/screenshot-dashboard.png)
![Pagina de registro do Fitcloak](../assets/images/screenshot-register.png)
