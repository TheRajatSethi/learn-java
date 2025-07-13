package rs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import rs.framework.Dependencies;
import rs.pojos.User;

public class CreateUserHandler implements Handler {

    ObjectMapper mapper = Dependencies.getObjectMapper();

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var user = mapper.readValue(ctx.body(), User.class);
        System.out.println(user);
    }
}
