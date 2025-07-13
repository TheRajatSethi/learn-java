package com.rs;


import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class Index {

    @Inject
    Template index;

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index(@Context ContainerRequestContext containerRequestContext){
        System.out.println(containerRequestContext);
        System.out.println(containerRequestContext.getPropertyNames());
        System.out.println(containerRequestContext.getProperty("a"));
        System.out.println(containerRequestContext.getSecurityContext().getUserPrincipal().getName());
        System.out.println(containerRequestContext.getSecurityContext().isUserInRole("admin"));
        return index.data(null);
    }

}