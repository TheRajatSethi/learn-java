import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import handlers.api.userHandler;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.sqlite.SQLiteDataSource;

public class Main {
    public static void main(String[] args){

        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:mem:test");

        HikariConfig hc = new HikariConfig();
        hc.setDataSource(ds);
        hc.setMaximumPoolSize(2);

        Javalin app = Javalin.create().start(5000);
        attachHandlers(app);

        // FIXME --> Now instead of using the Database class can use jdbi to make queries.

        Jdbi jdbi = Jdbi.create(new HikariDataSource(hc)).installPlugin(new SQLitePlugin());
    }

    public static void attachHandlers(Javalin app){
        app.addHandler(HandlerType.GET, "/api/user/{id}", new userHandler());
    }
}
