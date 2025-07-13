package com.rs;

import io.quarkus.qute.Template;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.logging.Log;
import org.jdbi.v3.core.Jdbi;

import java.util.UUID;

@Path("/register")
public class RegisterPage {

    @Inject
    Template register;

    @Inject
    Template partialMessage;

    @Inject
    Jdbi jdbi;

    @Inject
    BcryptPasswordProvider bcryptPasswordProvider;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(register.data(null)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("email") String email,
                                 @FormParam("name") String name,
                                 @FormParam("password") String password){
        Log.infof("Create User input email=%s name=%" +
                "s password=%s",email, name, password);

        // Input Validation
        if (email==null || name == null || password == null){
            Log.errorf("Create User Error null field detected email=%s name=%s password=%s", email, name, password);
            var ti = partialMessage.data("type", "warn").data("message", "error in form");
            return Response.ok(ti).build();
        }


        // Check for existing user
        var existingUser = jdbi.withHandle(handle -> handle.select("Select count(*) from users where email=?", email)
                .mapTo(Integer.class)
                .findFirst())
                .get(); // Todo no exception check here.

        System.out.println(existingUser);
        if (existingUser > 0){
            var ti = partialMessage.data("type", "warn").data("message", "User already exists");
            return Response.ok(ti).build();
        }

        // Create User
        var uuid = UUID.randomUUID().toString();
        var encryptedPassword = bcryptPasswordProvider.encode(password);

        var r = jdbi.withHandle(handle -> handle.execute("Insert into users (name, uuid, email, password) values (?, ?, ?, ?)",
                name, uuid, email, encryptedPassword));
        Log.infof("User creation affected rows %s", r);
        if (r==1){
            var ti= partialMessage.data("type", "hint").data("message", "User created proceed to login");
            return Response.ok(ti).build();
        }

        return Response.ok().build();
    }
}
