package handlers;

import io.javalin.http.ContentType;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.jetbrains.annotations.NotNull;
import services.UserService;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class SignupGetHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        UserService userService = UserService.getInstance();


        PebbleTemplate compiledTemplate = ViewTemplates.templates.get("signup.html");

        Writer writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        compiledTemplate.evaluate(writer, context);
        ctx.res().getWriter().write(writer.toString());
        ctx.res().setContentType(ContentType.HTML);
    }
}
