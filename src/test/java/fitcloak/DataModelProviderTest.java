package fitcloak;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataModelProviderTest {

    @TempDir
    File tempDir;

    @Test
    void createDataModel_missingConfig() throws IOException {
        Map<String, Object> model = DataModelProvider.createDataModel(tempDir, tempDir, "nonexistent.json");
        assertNotNull(model);
        assertNotNull(model.get("messagesPerField"));
        assertNotNull(model.get("msg"));
        assertNotNull(model.get("kcSanitize"));
    }

    @Test
    void createDataModel_loadsConfig() throws IOException {
        File config = new File(tempDir, "config.json");
        try (FileWriter fw = new FileWriter(config)) {
            fw.write("{\n");
            fw.write("  \"serverConfig\": { \"theme\": \"demo\", \"port\": 8080 },\n");
            fw.write("  \"realm\": { \"name\": \"test-realm\" }\n");
            fw.write("}\n");
        }

        Map<String, Object> model = DataModelProvider.createDataModel(tempDir, tempDir, "config.json");

        @SuppressWarnings("unchecked")
        Map<String, Object> realm = (Map<String, Object>) model.get("realm");
        assertEquals("test-realm", realm.get("name"));
    }

    @Test
    void createDataModel_defaultValues() throws IOException {
        File config = new File(tempDir, "config.json");
        try (FileWriter fw = new FileWriter(config)) {
            fw.write("{ \"serverConfig\": {} }");
        }

        Map<String, Object> model = DataModelProvider.createDataModel(tempDir, tempDir, "config.json");
        assertEquals(false, model.get("isAppInitiatedAction"));
        assertNotNull(model.get("requiredActions"));
        assertNotNull(model.get("register"));
    }

    @Test
    void createDataModel_loadsMockData() throws IOException {
        // Create theme directory with mock-data.json
        File themeDir = new File(tempDir, "mytheme");
        themeDir.mkdirs();

        File mockData = new File(themeDir, "mock-data.json");
        try (FileWriter fw = new FileWriter(mockData)) {
            fw.write("{ \"customField\": \"customValue\" }");
        }

        File config = new File(tempDir, "config.json");
        try (FileWriter fw = new FileWriter(config)) {
            fw.write("{ \"serverConfig\": { \"theme\": \"mytheme\" } }");
        }

        Map<String, Object> model = DataModelProvider.createDataModel(tempDir, tempDir, "config.json");
        assertEquals("customValue", model.get("customField"));
    }
}
