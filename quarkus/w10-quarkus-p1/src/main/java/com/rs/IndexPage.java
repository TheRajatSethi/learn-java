package com.rs;

import io.quarkus.qute.Template;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;

@Path("")
public class IndexPage {

    @Inject
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        return Response.ok(index.data(null)).build();
    }

}
