package rs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import rs.framework.Dependencies;
import rs.pojos.Page;

public class CreatePageHandler implements Handler {

    ObjectMapper mapper = Dependencies.getObjectMapper();

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var page = mapper.readValue(ctx.body(), Page.class);
        System.out.println(page);
    }
}
