import io.javalin.http.Context;
import io.javalin.http.Handler;
import model.User;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class IndexHandler implements Handler {
    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        ConfigFactory config = ConfigFactory.getConfig();

        User user = new User();
        user.name = "Sam";
        user.id = "s123";

        var data = new HashMap<String, Object>();
        data.put("someInteger", 10);
        data.put("someString", "someString");
        data.put("someUser", user);

        System.out.println(data.get("someInteger"));
        TimeUnit.MILLISECONDS.sleep(200);

        ctx.render("nav.jte", Map.of("user", user, "data", data));
    }
}
