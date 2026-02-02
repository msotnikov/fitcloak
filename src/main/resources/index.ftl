<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fitcloak - ${themeName}</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        ul { list-style-type: none; padding: 0; }
        li { margin: 5px 0; }
        a { text-decoration: none; color: #0066cc; }
        a:hover { text-decoration: underline; }
        h2 { margin-top: 30px; border-bottom: 1px solid #ccc; padding-bottom: 5px; }
        h3 { margin-top: 20px; color: #555; font-size: 1.1em; }
        .test-links { display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 20px; }
        .test-link { padding: 5px 10px; background: #f0f0f0; border: 1px solid #ccc; border-radius: 4px; font-size: 0.9em; }
        .test-link:hover { background: #e0e0e0; }
    </style>
</head>
<body>
    <h1>Theme: ${themeName}</h1>

    <h2>QA Quick Links (Login Page)</h2>
    <div class="test-links">
        <#list realmClients as realm, clients>
            <#if clients?has_content>
                <#list clients as client>
                    <a class="test-link" href="/login.ftl?realm.name=${realm}&client.clientId=${client}">${realm} : ${client}</a>
                </#list>
            <#else>
                <a class="test-link" href="/login.ftl?realm.name=${realm}">${realm} (default)</a>
            </#if>
        </#list>
    </div>

    <#list types as type>
        <h2>${type?cap_first}</h2>

        <#-- Custom -->
        <#if groupedTemplates[type]["Custom"]?has_content>
            <h3>Custom</h3>
            <ul>
                <#list groupedTemplates[type]["Custom"] as name>
                    <#assign linkName = name?replace(".ftl", "")>
                    <#assign href = "/" + linkName>
                    <#if type != "login">
                        <#assign href = "/" + type + "/" + linkName>
                    </#if>
                    <li><a href="${href}">${name}</a></li>
                </#list>
            </ul>
        </#if>

        <#-- Hierarchy -->
        <#list hierarchy[type] as parent>
            <#if parent != themeName && groupedTemplates[type][parent]?has_content>
                <h3>Inherited from <strong>${parent}</strong></h3>
                <ul>
                    <#list groupedTemplates[type][parent] as name>
                        <#assign linkName = name?replace(".ftl", "")>
                        <#assign href = "/" + linkName>
                        <#if type != "login">
                            <#assign href = "/" + type + "/" + linkName>
                        </#if>
                        <li><a href="${href}">${name}</a></li>
                    </#list>
                </ul>
            </#if>
        </#list>

        <#-- Base -->
        <#if groupedTemplates[type]["Base"]?has_content>
            <h3>Inherited from <strong>Base</strong></h3>
            <ul>
                <#list groupedTemplates[type]["Base"] as name>
                    <#assign linkName = name?replace(".ftl", "")>
                    <#assign href = "/" + linkName>
                    <#if type != "login">
                        <#assign href = "/" + type + "/" + linkName>
                    </#if>
                    <li><a href="${href}">${name}</a></li>
                </#list>
            </ul>
        </#if>

        <#-- Base (HTML) -->
        <#if groupedTemplates[type]["Base (HTML)"]?has_content>
            <h3>Inherited from <strong>Base (HTML)</strong></h3>
            <ul>
                <#list groupedTemplates[type]["Base (HTML)"] as name>
                    <#assign linkName = name?replace(".ftl", "")>
                    <#assign href = "/" + linkName>
                    <#if type != "login">
                        <#assign href = "/" + type + "/" + linkName>
                    </#if>
                    <li><a href="${href}">${name}</a></li>
                </#list>
            </ul>
        </#if>

        <#-- Base (Text) -->
        <#if groupedTemplates[type]["Base (Text)"]?has_content>
            <h3>Inherited from <strong>Base (Text)</strong></h3>
            <ul>
                <#list groupedTemplates[type]["Base (Text)"] as name>
                    <#assign linkName = name?replace(".ftl", "")>
                    <#assign href = "/" + linkName>
                    <#if type != "login">
                        <#assign href = "/" + type + "/" + linkName>
                    </#if>
                    <li><a href="${href}">${name}</a></li>
                </#list>
            </ul>
        </#if>

    </#list>
</body>
</html>
