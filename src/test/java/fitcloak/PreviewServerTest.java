package fitcloak;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PreviewServerTest {

    @Test
    void applyQueryParameters_simpleOverride() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "old");

        PreviewServer.applyQueryParameters(model, "name=new");
        assertEquals("new", model.get("name"));
    }

    @Test
    void applyQueryParameters_nestedOverride() {
        Map<String, Object> model = new HashMap<>();
        Map<String, Object> realm = new HashMap<>();
        realm.put("name", "old");
        model.put("realm", realm);

        PreviewServer.applyQueryParameters(model, "realm.name=newrealm");
        @SuppressWarnings("unchecked")
        Map<String, Object> updatedRealm = (Map<String, Object>) model.get("realm");
        assertEquals("newrealm", updatedRealm.get("name"));
    }

    @Test
    void applyQueryParameters_createsNestedMaps() {
        Map<String, Object> model = new HashMap<>();

        PreviewServer.applyQueryParameters(model, "a.b.c=value");

        @SuppressWarnings("unchecked")
        Map<String, Object> a = (Map<String, Object>) model.get("a");
        assertNotNull(a);
        @SuppressWarnings("unchecked")
        Map<String, Object> b = (Map<String, Object>) a.get("b");
        assertNotNull(b);
        assertEquals("value", b.get("c"));
    }

    @Test
    void applyQueryParameters_rejectsPathTraversal() {
        Map<String, Object> model = new HashMap<>();

        PreviewServer.applyQueryParameters(model, "../etc/passwd=evil");
        assertFalse(model.containsKey("../etc/passwd"));
        assertFalse(model.containsKey(".."));
    }

    @Test
    void applyQueryParameters_rejectsSlashes() {
        Map<String, Object> model = new HashMap<>();

        PreviewServer.applyQueryParameters(model, "path/to/file=evil");
        assertFalse(model.containsKey("path/to/file"));
    }

    @Test
    void applyQueryParameters_nullQuery() {
        Map<String, Object> model = new HashMap<>();
        PreviewServer.applyQueryParameters(model, null);
        assertTrue(model.isEmpty());
    }

    @Test
    void applyQueryParameters_emptyQuery() {
        Map<String, Object> model = new HashMap<>();
        PreviewServer.applyQueryParameters(model, "");
        assertTrue(model.isEmpty());
    }

    @Test
    void applyQueryParameters_multipleParams() {
        Map<String, Object> model = new HashMap<>();

        PreviewServer.applyQueryParameters(model, "a=1&b=2&c=3");
        assertEquals("1", model.get("a"));
        assertEquals("2", model.get("b"));
        assertEquals("3", model.get("c"));
    }

    @Test
    void getMimeType_css() {
        assertEquals("text/css", PreviewServer.getMimeType("style.css"));
    }

    @Test
    void getMimeType_javascript() {
        assertEquals("application/javascript", PreviewServer.getMimeType("app.js"));
    }

    @Test
    void getMimeType_svg() {
        assertEquals("image/svg+xml", PreviewServer.getMimeType("icon.svg"));
    }

    @Test
    void getMimeType_unknown() {
        assertEquals("application/octet-stream", PreviewServer.getMimeType("file.xyz"));
    }

    @Test
    void getMimeType_woff2() {
        assertEquals("font/woff2", PreviewServer.getMimeType("font.woff2"));
    }

    @Test
    void updateNestedValue_deepNesting() {
        Map<String, Object> map = new HashMap<>();
        PreviewServer.updateNestedValue(map, "a.b.c.d", "deep");

        @SuppressWarnings("unchecked")
        Map<String, Object> a = (Map<String, Object>) map.get("a");
        @SuppressWarnings("unchecked")
        Map<String, Object> b = (Map<String, Object>) a.get("b");
        @SuppressWarnings("unchecked")
        Map<String, Object> c = (Map<String, Object>) b.get("c");
        assertEquals("deep", c.get("d"));
    }
}
