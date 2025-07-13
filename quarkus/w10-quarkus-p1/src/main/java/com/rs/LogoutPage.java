package com.rs;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;

@Path("/logout")
public class LogoutPage {

    @Inject
    Jdbi jdbi;

    @GET
    public Response get(@Context ContainerRequestContext containerRequestContext){

        // Check for valid session.
        if (containerRequestContext.getProperty("userid") != null){
            var userId = (int) containerRequestContext.getProperty("userid");
            // Below will delete all user sessions from all devices.
            // If only 1 session or current session needs to be removed then use session id to delete
            jdbi.withHandle(handle -> handle.createUpdate("Delete from sessions where userId = ?").bind(0,userId).execute());

            NewCookie sc = new NewCookie.Builder("session")
                    .value("")
                    .maxAge(0)
                    .httpOnly(true)
                    .build();

            return Response.ok("User logged out").cookie(sc).build();
        }else{
            return Response.ok("no such session exists").build();
        }
    }
}
