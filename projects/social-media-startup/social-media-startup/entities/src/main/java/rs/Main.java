package rs;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.framework.Dependencies;
import rs.handlers.CreateAddressHandler;
import rs.handlers.CreateGroupHandler;
import rs.handlers.CreatePageHandler;
import rs.handlers.CreateUserHandler;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Start Initializing dependencies");
        Dependencies.initialize();
        log.info("Finished Initializing dependencies");
        
        log.info("Starting server in port {}", 7070);
        var app = Javalin.create(/*config*/)
                .post("/user", new CreateUserHandler())
                .post("/page", new CreatePageHandler())
                .post("/group", new CreateGroupHandler())
                .post("/address", new CreateAddressHandler())
                .post("/experience", new CreateAddressHandler())
                .start(7070);
    }
}
