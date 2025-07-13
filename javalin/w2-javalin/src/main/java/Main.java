import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;


public class Main {
    public static void main(String[] args) {
        // Set Default Log Level
        Logger logger = LoggerFactory.getLogger(Main.class);

        // Load Configuration
        ConfigFactory config = ConfigFactory.getConfig();

        // Setup Server
        var app = Javalin.create().start(config.port);
        logger.info("Server started at port {}", config.port);

        CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        JavalinJte.init(templateEngine);

        JavalinJte.init();

        // Register Handlers
        app.addHandler(HandlerType.GET, "/", new IndexHandler());
    }
}
