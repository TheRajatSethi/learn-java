# Manual integration of pebble templates.

## Pre-compiling templates and storing them in a constant

```java
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
        }
        ;
    }
}
```

This method can then be called from the main program before the server starts to compile the above templates.

```java
public class Main {
    public static void main(String[] args) {

        ViewTemplates.parseTemplates();
        var app = Javalin.create(/*config*/)
                .start(7070);

        app.addHandler(HandlerType.GET, "/", new Index());
    }
}
```

In the handler then you can use the precomipled template with whatever data you wish to render.

```java
package handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Index implements Handler {

    Logger logger = LoggerFactory.getLogger(Index.class);

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        logger.info("Hitting Index Handler - Do Get");

        PebbleTemplate compiledTemplate = ViewTemplates.templates.get("signup.html");

        Writer writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("name", "Sam");
        compiledTemplate.evaluate(writer, context);
        ctx.res().getWriter().write(writer.toString());
        ctx.res().setContentType("text/html");

    }
}
```

# Automatic Template Rendering

Use plugin - https://github.com/javalin/javalin-rendering/blob/main/src/main/java/io/javalin/rendering/template/JavalinPebble.kt

But there isn't much there in the plugin, this functionality can easily be replicated. 

# Testability and dependency injection

Javalin does not support DI by default however you can use various DI frameworks with it
- Google Guice
- Avaje
- Spring

The question is that for a small web application or possibly a mid size application should dependency be included.

__Links__

- https://www.reddit.com/r/java/comments/a574y4/annotation_based_javalin_applications_with/
- https://www.reddit.com/r/java/comments/10ru7v5/dependency_injection_frameworks/