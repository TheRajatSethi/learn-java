package com.rs;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
public class LoginPage {

    @Inject
    Template login;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return login.data(null);
    }

}
