package com.rs.User;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.util.List;


@Path("/register")
public class RegisterController {

    Logger LOG = Logger.getLogger(RegisterController.class);

    @Inject
    Jdbi jdbi;

    @Inject
    UserService userService;

    @Inject
    Template register;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getRegisterPage(){
        return Response.ok(register.data(null)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("email") String email,
                  @FormParam("name") String name,
                  @FormParam("password") String password){
        if (email==null || name == null || password == null){
            LOG.errorf("Create User Error null field detected email=%s name=%s password=%s", email, name, password);
            return Response.ok("Incorrect data").build();
        }
        var r =  userService.createUser(email, name, password);
        return Response.ok(r).build();
    }
}
