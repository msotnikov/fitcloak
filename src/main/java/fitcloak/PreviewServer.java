package fitcloak;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.core.HTMLOutputFormat;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Stream;
import freemarker.cache.TemplateLoader;

public class PreviewServer {

    private static final Logger log = LoggerFactory.getLogger(PreviewServer.class);

    private static final int DEFAULT_PORT = 3030;
    private static final String DEFAULT_CONFIG = "config.json";
    private static final String DEFAULT_KC_THEMES_PATH = "keycloak/base/themes/src/main/resources/theme";

    public static void main(String[] args) throws Exception {
        // Parse CLI arguments
        String configPath = DEFAULT_CONFIG;
        int port = -1; // -1 means "use config or default"
        String themeOverride = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--config":
                case "-c":
                    if (i + 1 < args.length) configPath = args[++i];
                    break;
                case "--port":
                case "-p":
                    if (i + 1 < args.length) port = Integer.parseInt(args[++i]);
                    break;
                case "--theme":
                case "-t":
                    if (i + 1 < args.length) themeOverride = args[++i];
                    break;
                case "--help":
                case "-h":
                    printUsage();
                    return;
                case "--version":
                case "-v":
                    printVersion();
                    return;
            }
        }

        File projectRoot = new File(".").getCanonicalFile();
        log.info("Project root: {}", projectRoot.getAbsolutePath());

        // Load initial config
        final String finalConfigPath = configPath;
        Map<String, Object> initialDataModel = DataModelProvider.createDataModel(projectRoot, null, finalConfigPath);
        Map<String, Object> serverConfig = getServerConfig(initialDataModel);

        // Resolve port: CLI > config > default
        if (port == -1) {
            Object configPort = serverConfig.get("port");
            if (configPort instanceof Number) {
                port = ((Number) configPort).intValue();
            } else {
                port = DEFAULT_PORT;
            }
        }

        // Resolve theme: CLI > config
        if (themeOverride != null) {
            serverConfig.put("theme", themeOverride);
        }

        String keycloakThemesPath = (String) serverConfig.getOrDefault("keycloakThemesPath", DEFAULT_KC_THEMES_PATH);
        File keycloakThemes = new File(projectRoot, keycloakThemesPath);
        if (!keycloakThemes.exists()) {
            log.warn("Keycloak themes not found at {}. Run setup-keycloak-themes.sh first.", keycloakThemes.getAbsolutePath());
        } else {
            log.info("Keycloak themes: {}", keycloakThemes.getAbsolutePath());
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        final int serverPort = port;
        server.createContext("/", new ThemeHandler(projectRoot, keycloakThemes, finalConfigPath));

        server.setExecutor(null);
        log.info("Fitcloak started at http://localhost:{}", serverPort);
        server.start();
    }

    private static void printUsage() {
        System.out.println("Fitcloak - Keycloak Theme Development Kit");
        System.out.println();
        System.out.println("Usage: fitcloak [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -c, --config <path>   Config file path (default: config.json)");
        System.out.println("  -p, --port <port>     Server port (default: 3030)");
        System.out.println("  -t, --theme <path>    Theme path (overrides config)");
        System.out.println("  -h, --help            Show this help");
        System.out.println("  -v, --version         Show version");
    }

    private static void printVersion() {
        String version = PreviewServer.class.getPackage().getImplementationVersion();
        System.out.println("Fitcloak " + (version != null ? version : "dev"));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getServerConfig(Map<String, Object> dataModel) {
        Object sc = dataModel.get("serverConfig");
        if (sc instanceof Map) {
            return (Map<String, Object>) sc;
        }
        return new HashMap<>();
    }

    static class ThemeHandler implements HttpHandler {
        private final File projectRoot;
        private final File keycloakThemes;
        private final String configPath;

        ThemeHandler(File projectRoot, File keycloakThemes, String configPath) {
            this.projectRoot = projectRoot;
            this.keycloakThemes = keycloakThemes;
            this.configPath = configPath;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().getPath();
            String query = t.getRequestURI().getQuery();
            log.debug("Request: {} {}", t.getRequestMethod(), uri + (query != null ? "?" + query : ""));

            if (uri.contains("/.well-known/")) {
                send404(t, "Not found");
                return;
            }

            Map<String, Object> dataModel;
            try {
                dataModel = DataModelProvider.createDataModel(projectRoot, keycloakThemes, configPath);
            } catch (Exception e) {
                log.error("Failed to load config", e);
                sendError(t, 500, "Failed to load configuration: " + e.getMessage());
                return;
            }

            // Apply query parameters to override data model
            applyQueryParameters(dataModel, query);

            Map<String, Object> config = getServerConfig(dataModel);
            String devResourcesUrl = (String) config.get("devResourcesUrl");

            // --- PROXY VITE INTERNALS & SOURCE ---
            if (devResourcesUrl != null && !devResourcesUrl.isEmpty()) {
                if (uri.startsWith("/node_modules/") || uri.startsWith("/@") || uri.startsWith("/src/")) {
                    String subPath = uri.substring(1);
                    if (tryServeFromDevServer(t, devResourcesUrl, subPath, query)) {
                        return;
                    }
                }
            }

            String themeName = (String) config.get("theme");
            if (themeName == null || themeName.isEmpty()) {
                send404(t, "Theme not configured. Set 'serverConfig.theme' in " + configPath);
                return;
            }

            File themeDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, themeName);
            if (!themeDir.exists()) {
                send404(t, "Theme not found: " + themeName + " at " + themeDir.getAbsolutePath());
                return;
            }

            String themeType = "login";
            String relativeUri = uri;

            if (uri.startsWith("/account/")) {
                themeType = "account";
                relativeUri = uri.substring("/account".length());
            } else if (uri.startsWith("/email/")) {
                themeType = "email";
                relativeUri = uri.substring("/email".length());
            } else if (uri.startsWith("/admin/")) {
                themeType = "admin";
                relativeUri = uri.substring("/admin".length());
            }

            // Determine hierarchy
            List<String> hierarchy = ThemeManager.getThemeHierarchy(projectRoot, keycloakThemes, themeName, themeType);

            // --- 1. Build Template Dictionary (Map<String, File>) ---
            Map<String, File> templateMap = new HashMap<>();

            for (int i = hierarchy.size() - 1; i >= 0; i--) {
                String tName = hierarchy.get(i);
                File tDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, tName);
                File dir = new File(tDir, themeType);

                if (dir.exists() && dir.isDirectory()) {
                    try (Stream<Path> stream = Files.walk(dir.toPath())) {
                        stream.filter(Files::isRegularFile)
                              .forEach(path -> {
                                  String relPath = dir.toPath().relativize(path).toString().replace("\\", "/");
                                  if (relPath.endsWith(".ftl")) {
                                      templateMap.put(relPath, path.toFile());
                                  }
                              });
                    } catch (IOException e) {
                        log.warn("Error scanning theme directory: {}", dir, e);
                    }
                }
            }

            // --- 2. Configure FreeMarker for this request ---
            Configuration requestCfg = new Configuration(Configuration.VERSION_2_3_32);
            requestCfg.setDefaultEncoding("UTF-8");
            requestCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            requestCfg.setLogTemplateExceptions(false);
            requestCfg.setWrapUncheckedExceptions(true);
            requestCfg.setFallbackOnNullLoopVariable(false);
            requestCfg.setOutputFormat(HTMLOutputFormat.INSTANCE);

            requestCfg.setTemplateLoader(new TemplateLoader() {
                public Object findTemplateSource(String name) {
                    return templateMap.get(name);
                }
                public long getLastModified(Object templateSource) {
                    return ((File) templateSource).lastModified();
                }
                public java.io.Reader getReader(Object templateSource, String encoding) throws IOException {
                    return new java.io.InputStreamReader(new java.io.FileInputStream((File) templateSource), encoding);
                }
                public void closeTemplateSource(Object templateSource) {
                    // nothing to do
                }
            });

            // --- 3. Prepare Properties ---
            Map<String, Object> finalProperties = new HashMap<>();
            for (int i = hierarchy.size() - 1; i >= 0; i--) {
                String tName = hierarchy.get(i);
                Map<String, String> props = ThemeManager.getThemeProperties(projectRoot, keycloakThemes, tName, themeType);
                finalProperties.putAll(props);
            }
            Map<String, Object> jsonProperties = (Map<String, Object>) dataModel.get("properties");
            if (jsonProperties != null) {
                finalProperties.putAll(jsonProperties);
            }
            dataModel.put("properties", finalProperties);

            // --- 4. Handle Resources with Hierarchy ---
            if (uri.startsWith("/resources/")) {
                String subPath = uri.substring("/resources/".length());

                // Try dev server first
                if (devResourcesUrl != null && !devResourcesUrl.isEmpty()) {
                    if (tryServeFromDevServer(t, devResourcesUrl, subPath, query)) {
                        return;
                    }
                }

                File foundRes = null;
                for (String tName : hierarchy) {
                    File tDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, tName);
                    File f = new File(tDir, themeType + "/resources/" + subPath);
                    if (f.exists()) { foundRes = f; break; }
                }

                if (foundRes != null) {
                    serveFile(t, foundRes);
                    return;
                }
            }

            // Handle common resources
            if (uri.startsWith("/resources/common/") || uri.startsWith("/resources-common/")) {
                String subPath;
                if (uri.startsWith("/resources/common/")) {
                    subPath = uri.substring("/resources/common".length());
                } else {
                    subPath = uri.substring("/resources-common".length());
                }

                File foundRes = null;
                for (String tName : hierarchy) {
                    File tDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, tName);
                    File localCommon = new File(tDir, "common/resources" + subPath);
                    if (localCommon.exists()) {
                        foundRes = localCommon;
                        break;
                    }
                }

                if (foundRes == null && keycloakThemes.exists()) {
                    File fallback = new File(keycloakThemes, "keycloak/common/resources" + subPath);
                    if (fallback.exists()) {
                        foundRes = fallback;
                    }
                }

                if (foundRes != null) {
                    serveFile(t, foundRes);
                    return;
                }

                send404(t, "Resource not found: " + uri);
                return;
            }

            if (uri.startsWith("/common/keycloak/")) {
                File keycloakCommonRes = new File(keycloakThemes, "keycloak/common/resources");
                String subPath = uri.replace("/common/keycloak/", "");
                serveFile(t, new File(keycloakCommonRes, subPath));
                return;
            }

            if (uri.equals("/") || uri.equals("/index")) {
                String response = IndexPageGenerator.generateIndexPage(projectRoot, themeName, keycloakThemes, config);
                sendHtml(t, 200, response);
                return;
            }

            // --- 5. Render Template ---
            String templateName = relativeUri;
            if (templateName.startsWith("/")) templateName = templateName.substring(1);
            if (templateName.isEmpty()) templateName = "login.ftl";
            if (!templateName.endsWith(".ftl")) templateName += ".ftl";

            try {
                Template temp = requestCfg.getTemplate(templateName);
                StringWriter out = new StringWriter();
                temp.process(dataModel, out);
                sendHtml(t, 200, out.toString());
            } catch (TemplateNotFoundException e) {
                log.warn("Template not found: {}", templateName);
                send404(t, "Template not found: " + templateName);
            } catch (Exception e) {
                log.error("Error processing template: {}", templateName, e);
                sendError(t, 500, "Template error in " + templateName + ": " + e.getMessage());
            }
        }
    }

    static boolean tryServeFromDevServer(HttpExchange t, String baseUrl, String subPath, String query) {
        try {
            String targetUrl = baseUrl;
            if (!targetUrl.endsWith("/")) targetUrl += "/";
            targetUrl += subPath;
            if (query != null && !query.isEmpty()) {
                targetUrl += "?" + query;
            }

            log.debug("Proxying to dev server: {}", targetUrl);

            java.net.URL url = new java.net.URL(targetUrl);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(500);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; Fitcloak DevProxy)");
            conn.setRequestProperty("Accept", "*/*");

            int code = conn.getResponseCode();
            if (code == 200) {
                String contentType = conn.getContentType();
                log.debug("Dev server hit: {} [{}]", targetUrl, contentType);

                if (contentType != null) {
                    t.getResponseHeaders().set("Content-Type", contentType);
                }

                long len = conn.getContentLengthLong();
                t.sendResponseHeaders(200, len > 0 ? len : 0);

                try (java.io.InputStream is = conn.getInputStream();
                     OutputStream os = t.getResponseBody()) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }
                return true;
            } else {
                log.debug("Dev server returned {}", code);
            }
        } catch (Exception e) {
            log.debug("Dev server miss: {}", e.getMessage());
        }
        return false;
    }

    static void serveFile(HttpExchange t, File file) throws IOException {
        if (!file.exists()) {
            send404(t, "File not found: " + file.getName());
            return;
        }

        String mime = getMimeType(file.getName());
        t.getResponseHeaders().set("Content-Type", mime);
        t.sendResponseHeaders(200, file.length());
        try (OutputStream os = t.getResponseBody()) {
            Files.copy(file.toPath(), os);
        }
    }

    static String getMimeType(String fileName) {
        String name = fileName.toLowerCase();
        if (name.endsWith(".css")) return "text/css";
        if (name.endsWith(".js")) return "application/javascript";
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".gif")) return "image/gif";
        if (name.endsWith(".svg")) return "image/svg+xml";
        if (name.endsWith(".webp")) return "image/webp";
        if (name.endsWith(".ico")) return "image/x-icon";
        if (name.endsWith(".woff")) return "font/woff";
        if (name.endsWith(".woff2")) return "font/woff2";
        if (name.endsWith(".ttf")) return "font/ttf";
        if (name.endsWith(".map")) return "application/json";
        if (name.endsWith(".json")) return "application/json";
        if (name.endsWith(".html")) return "text/html; charset=UTF-8";
        return "application/octet-stream";
    }

    static void sendHtml(HttpExchange t, int code, String html) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        t.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        t.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = t.getResponseBody()) {
            os.write(bytes);
        }
    }

    static void send404(HttpExchange t, String message) throws IOException {
        log.debug("404: {}", message);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(404, bytes.length);
        try (OutputStream os = t.getResponseBody()) {
            os.write(bytes);
        }
    }

    static void sendError(HttpExchange t, int code, String message) throws IOException {
        log.error("{}: {}", code, message);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = t.getResponseBody()) {
            os.write(bytes);
        }
    }

    static void applyQueryParameters(Map<String, Object> dataModel, String query) {
        if (query == null || query.isEmpty()) return;

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                try {
                    String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);

                    // Basic validation: reject keys that look like path traversal
                    if (key.contains("..") || key.contains("/") || key.contains("\\")) {
                        log.warn("Rejected suspicious query parameter: {}", key);
                        continue;
                    }

                    updateNestedValue(dataModel, key, value);
                } catch (Exception e) {
                    log.warn("Error parsing query param: {}", pair);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    static void updateNestedValue(Map<String, Object> map, String key, String value) {
        String[] parts = key.split("\\.");
        Map<String, Object> current = map;

        for (int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            Object obj = current.get(part);
            if (obj instanceof Map) {
                current = (Map<String, Object>) obj;
            } else {
                Map<String, Object> newMap = new HashMap<>();
                current.put(part, newMap);
                current = newMap;
            }
        }
        current.put(parts[parts.length - 1], value);
    }
}
