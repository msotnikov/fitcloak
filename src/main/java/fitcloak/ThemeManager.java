package fitcloak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ThemeManager {

    private static final Logger log = LoggerFactory.getLogger(ThemeManager.class);
    private static final int MAX_HIERARCHY_DEPTH = 20;

    public static List<String> getThemeHierarchy(File projectRoot, File keycloakThemes, String themeName, String type) {
        List<String> hierarchy = new ArrayList<>();
        String current = themeName;
        while (current != null && !current.isEmpty()) {
            hierarchy.add(current);
            if ("base".equals(current)) break;

            Map<String, String> props = getThemeProperties(projectRoot, keycloakThemes, current, type);
            String parent = props.get("parent");
            if (parent == null || parent.trim().isEmpty() || parent.equals(current)) {
                break;
            }
            current = parent;
            if (hierarchy.size() > MAX_HIERARCHY_DEPTH) {
                log.warn("Theme hierarchy exceeded {} levels, possible cycle detected for theme: {}", MAX_HIERARCHY_DEPTH, themeName);
                break;
            }
        }
        return hierarchy;
    }

    public static Map<String, String> getThemeProperties(File projectRoot, File keycloakThemes, String themeName, String type) {
        File themeDir = resolveThemeDir(projectRoot, keycloakThemes, themeName);
        File props = new File(themeDir, type + "/theme.properties");
        if (props.exists()) return loadProperties(props);
        return new HashMap<>();
    }

    private static Map<String, String> loadProperties(File file) {
        Map<String, String> props = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(file)) {
            Properties p = new Properties();
            p.load(fis);
            for (String name : p.stringPropertyNames()) {
                props.put(name, p.getProperty(name));
            }
        } catch (IOException e) {
            log.error("Failed to load properties from {}", file.getAbsolutePath(), e);
        }
        return props;
    }

    public static Map<String, String> getMessages(File projectRoot, File keycloakThemes, String themeName, String type, String locale) {
        List<String> hierarchy = getThemeHierarchy(projectRoot, keycloakThemes, themeName, type);
        Map<String, String> messages = new HashMap<>();

        // Iterate from base to current (base has lowest priority)
        for (int i = hierarchy.size() - 1; i >= 0; i--) {
            String tName = hierarchy.get(i);
            File themeDir = resolveThemeDir(projectRoot, keycloakThemes, tName);
            loadMessages(messages, new File(themeDir, type + "/messages/messages_" + locale + ".properties"));
        }
        return messages;
    }

    private static void loadMessages(Map<String, String> messages, File file) {
        if (file.exists()) {
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                Properties p = new Properties();
                p.load(reader);
                for (String name : p.stringPropertyNames()) {
                    messages.put(name, p.getProperty(name));
                }
            } catch (IOException e) {
                log.error("Failed to load messages from {}", file.getAbsolutePath(), e);
            }
        }
    }

    public static File resolveThemeDir(File projectRoot, File keycloakThemes, String themeRef) {
        File f = new File(themeRef);
        if (f.isAbsolute()) {
            return f;
        }

        if (!"keycloak".equals(themeRef) && !"src".equals(themeRef) && !"build".equals(themeRef)) {
            File relative = new File(projectRoot, themeRef);
            if (relative.exists() && relative.isDirectory()) {
                return relative;
            }
        }

        // If it looks like a path (e.g. "./foo" or "foo/bar"), treat as path relative to root
        if (themeRef.contains(File.separator) || themeRef.contains("/")) {
            File relative = new File(projectRoot, themeRef);
            if (relative.exists() && relative.isDirectory()) {
                return relative;
            }
        }

        // Keycloak standard themes
        if (keycloakThemes != null) {
            File kcTheme = new File(keycloakThemes, themeRef);
            if (kcTheme.exists() && kcTheme.isDirectory()) {
                return kcTheme;
            }
        }

        // Default to relative path for error reporting
        return new File(projectRoot, themeRef);
    }
}
