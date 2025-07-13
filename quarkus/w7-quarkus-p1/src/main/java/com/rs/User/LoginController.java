package com.rs.User;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @Inject
    Template login;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index(){

        // Is the person already logged in -> In that case redirect to somewhere else
        return Response.ok(login.data(null)).build();
    }
}
