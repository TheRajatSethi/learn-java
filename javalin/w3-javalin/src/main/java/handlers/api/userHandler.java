package handlers.api;

import com.google.gson.Gson;
import db.Database;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userHandler implements Handler {
    @Override
    public void handle(@NotNull Context context) throws Exception {

        Gson gson = new Gson();

        Connection connection = Database.getConnection();

        PreparedStatement ps = connection.prepareStatement("Select * from users where id = ?");
        ps.setString(1, context.pathParam("id"));
        ResultSet rs = ps.executeQuery();

        var result = Database.convertToObjectSingle(rs);

        context.result(gson.toJson(result));
    }
}
