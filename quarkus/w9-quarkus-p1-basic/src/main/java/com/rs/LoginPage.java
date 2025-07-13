package com.rs;

import com.rs.models.Session;
import com.rs.models.User;
import io.quarkus.logging.Log;
import io.quarkus.qute.Template;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;

import java.time.ZonedDateTime;
import java.util.UUID;


@Path("/login")
public class LoginPage {

    @Inject
    Template login;

    @Inject
    Jdbi jdbi;

    @Inject
    BcryptPasswordProvider bcryptPasswordProvider;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(login.data(null)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @Context ContainerRequestContext containerRequestContext){

        // Check for valid session.
        if (containerRequestContext.getProperty("userid") != null){
            return Response.ok("User already has a valid session").build();
        }

        if (email == null || password == null){
            return Response.ok("Invalid Form").build();
        }

        var user = jdbi.withHandle(handle -> handle.select("Select * from Users where email = ?", email).mapToBean(User.class).findFirst());

        if (user.isPresent()){
            if( bcryptPasswordProvider.matches(password, user.get().getPassword())){

                Log.infof("User %s", user.get());

                Session s = Session.builder()
                        .id(UUID.randomUUID().toString())
                        .created(ZonedDateTime.now())
                        .max(ZonedDateTime.now().plusDays(30))
                        .userId(user.get().getId())
                        .build();

                jdbi.withHandle(handle -> handle.createUpdate("Insert into sessions (id, created, max, userId) values (:id, :created, :max, :userId)")
                        .bindBean(s).execute());

                NewCookie sc = new NewCookie.Builder("session")
                        .value(s.getId())
                        .httpOnly(true)
                        .build();

                return Response.ok("User authenticated").cookie(sc).build();
            }
        }
            return Response.ok("No such user password combination exists").build();
    }
}
