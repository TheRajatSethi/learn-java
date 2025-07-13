import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import handlers.HomeHandler;
import handlers.SignupGetHandler;
import handlers.ViewTemplates;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import models.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.sqlite.SQLiteDataSource;

public class Main {

    public static Jdbi jdbi;
    public static Javalin app;

    public static void main(String[] args) {

        connectToDb();
        ViewTemplates.parseTemplates();



        app = Javalin.create(/*config*/)
                .start(7070);
        addHandlers();

    }

    static void addHandlers(){

        app.addHandler(HandlerType.GET, "/", new HomeHandler());
        app.addHandler(HandlerType.GET, "/signup", new SignupGetHandler());

    }

    static void connectToDb(){
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl("jdbc:sqlite:database.db");

        HikariConfig hc = new HikariConfig();
        hc.setDataSource(ds);
        hc.setMaximumPoolSize(2);

        jdbi = Jdbi.create(new HikariDataSource(hc)).installPlugin(new SQLitePlugin());

        var r = jdbi.withHandle(handle -> handle.createQuery("SELECT * from \"User\"")
                .registerRowMapper(ConstructorMapper.factory(User.class))
                .mapTo(User.class)
                .stream().toList());
        System.out.println(r);
    }


}


