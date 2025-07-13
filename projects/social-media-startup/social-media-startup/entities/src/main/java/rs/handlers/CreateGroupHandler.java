package rs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import rs.framework.Dependencies;
import rs.pojos.Group;

public class CreateGroupHandler implements Handler {

    ObjectMapper mapper = Dependencies.getObjectMapper();

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var group = mapper.readValue(ctx.body(), Group.class);
        System.out.println(group);
    }
}
