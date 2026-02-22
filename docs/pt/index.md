---
title: Inicio
nav_exclude: true
lang: pt
permalink: /pt/
---

# Fitcloak

Um servidor de pre-visualizacao local e leve para desenvolver **temas nativos Keycloak com FreeMarker** -- sem Docker, banco de dados ou execucao de uma instancia Keycloak completa.

[Comecar](../quick-start/) [Ver no GitHub](https://github.com/msotnikov/fitcloak)

---

## Por que Fitcloak?

Personalizar as paginas de login/conta/email do Keycloak normalmente significa um ciclo de feedback doloroso: reconstruir o JAR, reiniciar o Keycloak, limpar caches, atualizar. O Fitcloak elimina tudo isso -- basta salvar o arquivo e ver o resultado.

**Pegue qualquer template do Keycloak, aponte o Fitcloak para ele e comece a trabalhar.** O proxy do servidor de desenvolvimento integrado permite usar qualquer cadeia de ferramentas frontend -- Vite, Webpack, Parcel -- com qualquer framework ou pre-processador: React, Vue, Svelte, SCSS, Tailwind, o que voce preferir. O FreeMarker renderiza a estrutura da pagina, suas ferramentas cuidam do frontend, e o HMR mantem o ciclo de feedback instantaneo.

Isso proporciona toda a flexibilidade do desenvolvimento frontend moderno permanecendo dentro do sistema de temas nativo do Keycloak: sem SPIs personalizados, sem dependencia de fornecedor -- apenas templates `.ftl` padrao que fazem deploy em qualquer instancia Keycloak como estao.

### Fitcloak vs Keycloakify

| | Fitcloak | [Keycloakify](https://github.com/keycloakify/keycloakify) |
|---|---|---|
| **Abordagem** | Templates FreeMarker nativos + qualquer ferramenta frontend | Componentes React compilados em temas |
| **Caso de uso** | Personalizar temas Keycloak padrao com DX moderna | Construir interfaces completamente novas com React |
| **Frontend** | Qualquer framework (React, Vue, Svelte, Alpine.js, vanilla) ou nenhum | Apenas React/TypeScript |
| **Curva de aprendizado** | Conhece FreeMarker = pronto para comecar | Requer conhecimento de React/TypeScript |
| **Resultado** | Diretorio de tema padrao (funciona em qualquer Keycloak) | JAR com aplicacao React compilada |

O Keycloakify segue um caminho diferente: substitui completamente o FreeMarker por uma SPA React e tem seu proprio pipeline de build. O Fitcloak trabalha com o sistema de temas padrao do Keycloak -- os mesmos templates `.ftl`, o mesmo deployment, apenas um fluxo de trabalho de desenvolvimento muito melhor.

## Funcionalidades

- **Feedback instantaneo** -- edite `.ftl` / `.css` / `.properties`, atualize o navegador
- **Heranca completa** -- emula a cadeia de temas `Base -> Parent -> Child` do Keycloak
- **Integracao Vite/HMR** -- proxy para um servidor de desenvolvimento para hot module replacement
- **Testes dinamicos** -- sobrescreva qualquer variavel de template via parametros de consulta URL
- **Painel de controle** -- navegador de templates com visualizacao de heranca e links de QA
- **Zero infraestrutura** -- apenas Java e Gradle, nada mais

![Painel de controle do Fitcloak](../assets/images/screenshot-dashboard.png)
![Pagina de registro do Fitcloak](../assets/images/screenshot-register.png)
