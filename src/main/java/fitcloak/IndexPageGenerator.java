package fitcloak;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexPageGenerator {

    private static final Logger log = LoggerFactory.getLogger(IndexPageGenerator.class);

    @SuppressWarnings("unchecked")
    public static String generateIndexPage(File projectRoot, String themeName, File keycloakThemes, Map<String, Object> serverConfig) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setClassForTemplateLoading(IndexPageGenerator.class, "/");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            Template template = cfg.getTemplate("index.ftl");

            Map<String, Object> data = new HashMap<>();
            data.put("themeName", themeName);

            // --- QA Links Data ---
            Map<String, List<String>> realmClients = new HashMap<>();

            if (serverConfig.containsKey("qaRealms")) {
                Map<String, Object> qaRealms = (Map<String, Object>) serverConfig.get("qaRealms");
                for (Map.Entry<String, Object> entry : qaRealms.entrySet()) {
                    List<String> clients = (List<String>) entry.getValue();
                    realmClients.put(entry.getKey(), clients);
                }
            } else {
                realmClients.put("master", new ArrayList<>());
            }

            data.put("realmClients", realmClients);

            String[] types = {"login", "account", "email", "admin"};
            data.put("types", types);

            Map<String, List<String>> hierarchyMap = new HashMap<>();
            Map<String, Map<String, List<String>>> groupedTemplates = new HashMap<>();

            for (String type : types) {
                List<String> hierarchy = ThemeManager.getThemeHierarchy(projectRoot, keycloakThemes, themeName, type);
                hierarchyMap.put(type, hierarchy);

                Map<String, String> templates = new HashMap<>();

                // 1. Base
                File baseDir = new File(keycloakThemes, "base/" + type);
                if (baseDir.exists()) {
                    File[] files = baseDir.listFiles((dir, name) -> name.endsWith(".ftl"));
                    if (files != null) {
                        for (File f : files) templates.put(f.getName(), "Base");
                    }
                    if ("email".equals(type)) {
                        File htmlDir = new File(baseDir, "html");
                        if (htmlDir.exists()) {
                            File[] htmlFiles = htmlDir.listFiles((dir, name) -> name.endsWith(".ftl"));
                            if (htmlFiles != null) {
                                for (File f : htmlFiles) templates.put(f.getName(), "Base (HTML)");
                            }
                        }
                        File textDir = new File(baseDir, "text");
                        if (textDir.exists()) {
                            File[] textFiles = textDir.listFiles((dir, name) -> name.endsWith(".ftl"));
                            if (textFiles != null) {
                                for (File f : textFiles) templates.put(f.getName(), "Base (Text)");
                            }
                        }
                    }
                }

                // 2. Hierarchy (from base to current)
                for (int i = hierarchy.size() - 1; i >= 0; i--) {
                    String parent = hierarchy.get(i);
                    File themeDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, parent);
                    File localDir = new File(themeDir, type);
                    if (localDir.exists()) {
                        File[] files = localDir.listFiles((dir, name) -> name.endsWith(".ftl"));
                        if (files != null) {
                            for (File f : files) templates.put(f.getName(), parent);
                        }
                    }
                }

                // 3. Custom (current theme)
                File themeDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, themeName);
                File customDir = new File(themeDir, type);
                if (customDir.exists()) {
                    File[] files = customDir.listFiles((dir, name) -> name.endsWith(".ftl"));
                    if (files != null) {
                        for (File f : files) templates.put(f.getName(), "Custom");
                    }
                }

                // Group by source
                Map<String, List<String>> grouped = new HashMap<>();
                grouped.put("Custom", new ArrayList<>());
                grouped.put("Base", new ArrayList<>());
                grouped.put("Base (HTML)", new ArrayList<>());
                grouped.put("Base (Text)", new ArrayList<>());
                for (String p : hierarchy) {
                    grouped.put(p, new ArrayList<>());
                }

                for (Map.Entry<String, String> entry : templates.entrySet()) {
                    String source = entry.getValue();
                    if (!grouped.containsKey(source)) grouped.put(source, new ArrayList<>());
                    grouped.get(source).add(entry.getKey());
                }

                for (List<String> list : grouped.values()) {
                    Collections.sort(list);
                }

                groupedTemplates.put(type, grouped);
            }

            data.put("hierarchy", hierarchyMap);
            data.put("groupedTemplates", groupedTemplates);

            StringWriter out = new StringWriter();
            template.process(data, out);
            return out.toString();

        } catch (Exception e) {
            log.error("Error generating index page", e);
            return "<html><body><h1>Error generating index page</h1><pre>" + e.getMessage() + "</pre></body></html>";
        }
    }
}
