package handlers;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ViewTemplates {

    public static Map<String, PebbleTemplate> templates = new HashMap<>();
    public static PebbleEngine engine = new PebbleEngine.Builder().build();
    static final Logger logger = LoggerFactory.getLogger(ViewTemplates.class);

    public static void parseTemplates() {
        try {
            var files = Files.list(Paths.get("src", "main", "resources", "templates").toAbsolutePath());
            files.forEach(f -> {
                templates.put(f.getFileName().toString(), engine.getTemplate(f.toAbsolutePath().toString()));
            });
        } catch (Exception e) {
            logger.error("Unable to locate templates");
            System.exit(1);
        };
    }
}
