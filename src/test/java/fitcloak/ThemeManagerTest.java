package fitcloak;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThemeManagerTest {

    @TempDir
    File tempDir;

    @Test
    void resolveThemeDir_absolutePath() {
        File result = ThemeManager.resolveThemeDir(tempDir, tempDir, "/absolute/path");
        assertEquals("/absolute/path", result.getPath());
    }

    @Test
    void resolveThemeDir_relativePath() throws IOException {
        File themeDir = new File(tempDir, "themes/mytheme");
        themeDir.mkdirs();

        File result = ThemeManager.resolveThemeDir(tempDir, tempDir, "themes/mytheme");
        assertEquals(themeDir.getAbsolutePath(), result.getAbsolutePath());
    }

    @Test
    void resolveThemeDir_keycloakTheme() throws IOException {
        File kcThemes = new File(tempDir, "keycloak-themes");
        File baseTheme = new File(kcThemes, "base");
        baseTheme.mkdirs();

        File result = ThemeManager.resolveThemeDir(tempDir, kcThemes, "base");
        assertEquals(baseTheme.getAbsolutePath(), result.getAbsolutePath());
    }

    @Test
    void getThemeProperties_existingFile() throws IOException {
        File themeDir = new File(tempDir, "mytheme/login");
        themeDir.mkdirs();

        File propsFile = new File(themeDir, "theme.properties");
        try (FileWriter fw = new FileWriter(propsFile)) {
            fw.write("parent=keycloak\n");
            fw.write("styles=css/login.css\n");
        }

        Map<String, String> props = ThemeManager.getThemeProperties(tempDir, tempDir, "mytheme", "login");
        assertEquals("keycloak", props.get("parent"));
        assertEquals("css/login.css", props.get("styles"));
    }

    @Test
    void getThemeProperties_missingFile() {
        Map<String, String> props = ThemeManager.getThemeProperties(tempDir, tempDir, "nonexistent", "login");
        assertTrue(props.isEmpty());
    }

    @Test
    void getThemeHierarchy_singleTheme() {
        List<String> hierarchy = ThemeManager.getThemeHierarchy(tempDir, tempDir, "mytheme", "login");
        assertEquals(1, hierarchy.size());
        assertEquals("mytheme", hierarchy.get(0));
    }

    @Test
    void getThemeHierarchy_withParent() throws IOException {
        // Create child theme with parent=base
        File childDir = new File(tempDir, "child/login");
        childDir.mkdirs();
        try (FileWriter fw = new FileWriter(new File(childDir, "theme.properties"))) {
            fw.write("parent=base\n");
        }

        List<String> hierarchy = ThemeManager.getThemeHierarchy(tempDir, tempDir, "child", "login");
        assertEquals(2, hierarchy.size());
        assertEquals("child", hierarchy.get(0));
        assertEquals("base", hierarchy.get(1));
    }

    @Test
    void getThemeHierarchy_preventsInfiniteLoop() throws IOException {
        // Create theme that references itself
        File selfRef = new File(tempDir, "selfref/login");
        selfRef.mkdirs();
        try (FileWriter fw = new FileWriter(new File(selfRef, "theme.properties"))) {
            fw.write("parent=selfref\n");
        }

        List<String> hierarchy = ThemeManager.getThemeHierarchy(tempDir, tempDir, "selfref", "login");
        assertEquals(1, hierarchy.size());
    }

    @Test
    void getMessages_mergesHierarchy() throws IOException {
        File kcThemes = new File(tempDir, "kc");

        // Create base theme messages
        File baseDir = new File(kcThemes, "base/login/messages");
        baseDir.mkdirs();
        try (FileWriter fw = new FileWriter(new File(baseDir, "messages_en.properties"))) {
            fw.write("loginTitle=Sign In\n");
            fw.write("usernameLabel=Username\n");
        }

        // Create child theme with parent=base and overriding one message
        File childDir = new File(tempDir, "child/login");
        childDir.mkdirs();
        try (FileWriter fw = new FileWriter(new File(childDir, "theme.properties"))) {
            fw.write("parent=base\n");
        }
        File childMsgs = new File(tempDir, "child/login/messages");
        childMsgs.mkdirs();
        try (FileWriter fw = new FileWriter(new File(childMsgs, "messages_en.properties"))) {
            fw.write("loginTitle=Welcome Back\n");
        }

        Map<String, String> messages = ThemeManager.getMessages(tempDir, kcThemes, "child", "login", "en");
        assertEquals("Welcome Back", messages.get("loginTitle"));
        assertEquals("Username", messages.get("usernameLabel"));
    }
}
