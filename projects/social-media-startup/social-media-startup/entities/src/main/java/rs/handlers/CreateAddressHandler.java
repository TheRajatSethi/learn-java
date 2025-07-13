package rs.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import rs.framework.Dependencies;
import rs.pojos.AddressWithDate;

public class CreateAddressHandler implements Handler {

    ObjectMapper mapper = Dependencies.getObjectMapper();

    @Override
    public void handle(@NotNull Context context) throws Exception {
        var addressWithDate = mapper.readValue(context.body(), AddressWithDate.class);
        System.out.println(addressWithDate);
    }
}
