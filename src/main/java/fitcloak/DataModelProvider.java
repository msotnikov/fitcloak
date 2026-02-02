package fitcloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModelProvider {

    private static final Logger log = LoggerFactory.getLogger(DataModelProvider.class);
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static Map<String, Object> createDataModel(File projectRoot, File keycloakThemes, String configPath) throws IOException {
        File mainConfigFile = new File(projectRoot, configPath);
        Map<String, Object> root = new HashMap<>();
        Map<String, Object> serverConfig = new HashMap<>();

        if (mainConfigFile.exists()) {
            Map<String, Object> mainConfig = jsonMapper.readValue(mainConfigFile, Map.class);
            root.putAll(mainConfig);
            if (mainConfig.containsKey("serverConfig")) {
                serverConfig = (Map<String, Object>) mainConfig.get("serverConfig");
            }
        } else {
            log.warn("Config file not found: {}. Copy config.example.json to {} to get started.", mainConfigFile.getAbsolutePath(), configPath);
        }

        String themeName = (String) serverConfig.get("theme");
        log.debug("Theme: {}", themeName);

        if (themeName != null && !themeName.isEmpty() && keycloakThemes != null) {
            File themeDir = ThemeManager.resolveThemeDir(projectRoot, keycloakThemes, themeName);
            File themeMockFile = new File(themeDir, "mock-data.json");
            if (themeMockFile.exists()) {
                log.debug("Loading theme mock data from {}", themeMockFile.getAbsolutePath());
                Map<String, Object> themeMockData = jsonMapper.readValue(themeMockFile, Map.class);
                root.putAll(themeMockData);
            }
        }

        root.put("serverConfig", serverConfig);

        // Determine locale
        String currentLocale = "en";
        if (root.containsKey("locale")) {
            Map<String, Object> localeMap = (Map<String, Object>) root.get("locale");
            if (localeMap != null && localeMap.containsKey("current")) {
                currentLocale = (String) localeMap.get("current");
            }
        }

        // Load messages
        if (themeName != null && !themeName.isEmpty() && keycloakThemes != null) {
            Map<String, String> messages = ThemeManager.getMessages(projectRoot, keycloakThemes, themeName, "login", currentLocale);

            root.put("msg", (TemplateMethodModelEx) arguments -> {
                if (arguments.isEmpty()) return "";
                String key = arguments.get(0).toString();
                return messages.getOrDefault(key, key);
            });
        } else {
            root.put("msg", (TemplateMethodModelEx) arguments -> {
                if (arguments.isEmpty()) return "";
                return arguments.get(0).toString();
            });
        }

        // advancedMsg: same as msg but used in user-profile-commons.ftl and other templates
        root.put("advancedMsg", root.get("msg"));

        root.put("kcSanitize", (TemplateMethodModelEx) arguments -> {
            if (arguments.isEmpty()) return "";
            return arguments.get(0).toString();
        });

        if (root.containsKey("auth") && root.get("auth") instanceof Map) {
            Map<String, Object> auth = (Map<String, Object>) root.get("auth");
            auth.put("showUsername", (TemplateMethodModelEx) args -> true);
            auth.put("showResetCredentials", (TemplateMethodModelEx) args -> true);
            auth.put("showTryAnotherWayLink", (TemplateMethodModelEx) args -> false);
        }

        Map<String, Object> messagesPerField = new HashMap<>();
        messagesPerField.put("existsError", (TemplateMethodModelEx) args -> false);
        messagesPerField.put("exists", (TemplateMethodModelEx) args -> false);
        messagesPerField.put("get", (TemplateMethodModelEx) args -> "");
        messagesPerField.put("printIfExists", (TemplateMethodModelEx) args -> "");
        messagesPerField.put("getFirstError", (TemplateMethodModelEx) args -> "");
        root.put("messagesPerField", messagesPerField);

        if (!root.containsKey("isAppInitiatedAction")) root.put("isAppInitiatedAction", false);
        if (!root.containsKey("requiredActions")) root.put("requiredActions", new ArrayList<>());
        if (!root.containsKey("register")) {
            Map<String, Object> reg = new HashMap<>();
            reg.put("formData", new HashMap<>());
            root.put("register", reg);
        }

        return root;
    }
}
