package handlers;

import io.javalin.http.ContentType;
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

public class HomeHandler implements Handler {

    Logger logger = LoggerFactory.getLogger(HomeHandler.class);

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        logger.info("Hitting Index Handler - Do Get");

        PebbleTemplate compiledTemplate = ViewTemplates.templates.get("index.html");

        Writer writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        compiledTemplate.evaluate(writer, context);
        ctx.res().getWriter().write(writer.toString());
        ctx.res().setContentType(ContentType.HTML);
    }
}
