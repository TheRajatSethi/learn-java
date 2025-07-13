package com.rs;

import com.rs.models.Session;
import io.quarkus.arc.Arc;
import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.ext.Provider;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.time.ZonedDateTime;

@Provider
@PreMatching
public class CheckUser implements ContainerRequestFilter {

    /**
     * The below does not work some limitation with jax-rs resteasy.
        @Inject
        public CheckUser(Jdbi jdbi){
            this.jdbi = jdbi;
        }
    **/
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        var jdbi = Arc.container().instance(Jdbi.class).get();

        Cookie c;
        if (requestContext.getCookies().containsKey("session")){
            c = requestContext.getCookies().get("session");
            Log.infof("Existing cookie %s",c);
        }else{
            return;
        }

        var optionalSession = jdbi.withHandle(handle -> handle.select("Select * from Sessions where id = ?", c.getValue()).mapToBean(Session.class).findFirst());
        if (optionalSession.isPresent()){
            var session = optionalSession.get();
            Log.infof("Existing Session %s", session);
            if (ZonedDateTime.now().compareTo(session.getMax()) < 0){ // valid session
                requestContext.setProperty("userid", session.getUserId());
            }else{
                jdbi.withHandle(handle -> handle.createUpdate("Delete from session where id = ?").bind(0,session.getId())).execute();
            }
        }
    }
}
