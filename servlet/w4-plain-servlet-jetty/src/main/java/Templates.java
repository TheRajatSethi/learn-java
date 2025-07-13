import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Templates {

    public static Map<String, PebbleTemplate> templates = new HashMap<>();
    public static PebbleEngine engine = new PebbleEngine.Builder().build();

    public static void parseTemplates() throws IOException {
        var files = Files.list(Paths.get("src","main","resources","templates").toAbsolutePath());
        files.forEach( f -> {
            templates.put(f.getFileName().toString(), engine.getTemplate(f.toAbsolutePath().toString()));
        });
    }
}
