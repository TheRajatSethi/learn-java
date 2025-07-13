package main

import io.javalin.Javalin
import io.javalin.http.Context
import io.javalin.http.Handler
import io.pebbletemplates.pebble.PebbleEngine
import kotlinx.datetime.Instant
import java.sql.DriverManager
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import java.sql.Types


/**
 * ```
 * Setting up the app
 * - TODO Sqlite - Foreign key on, timeout
 */
object App {
    val javalinApp= Javalin.create {}!!
    private val engine = PebbleEngine.Builder().build()!!
    val compiledIndexTemplate = engine.getTemplate("templates/index.peb")!!;
    private val con = DriverManager.getConnection("jdbc:sqlite:db.db")!!
    val jdbi = Jdbi.create(con).installPlugins()!!
}

private class InstantArgumentFactory : AbstractArgumentFactory<kotlinx.datetime.Instant>(Types.OTHER) {
    override fun build(value: Instant, config: ConfigRegistry?): Argument =
        Argument { position, statement, _ ->
            statement.setObject(position, value.toString())
        }
}

fun jdbiMappers(){
    App.jdbi.registerColumnMapper(Instant::class.java) { rs, col, ctx ->
        Instant.parse(rs.getString(col))
    }
    App.jdbi.registerArgument(InstantArgumentFactory())
}

fun routing() {
    // Site Views
    App.javalinApp.get("/", Index())
    App.javalinApp.get("/about", About())
    App.javalinApp.post("/contact", Contact())
    App.javalinApp.get("/search", Search())
    
    // scratch.Account Views/
    App.javalinApp.get("/signup", SignUpGetHandler())
    App.javalinApp.post("/signup", SignUpPostHandler())
    App.javalinApp.get("/signin", SignInGetHandler())
    App.javalinApp.post("/signin", SignInPostHandler())
}


fun main() {
    register();
    jdbiMappers()
    routing()

    App.javalinApp.start(8080)


//    val config = HikariConfig()
//    config.jdbcUrl = "jdbc:sqlite:db.db"
//    val ds = HikariDataSource(config)

}


class Search : Handler {
    override fun handle(ctx: Context) {
        ctx.result("main.Search")
    }
}

class Dashboard : Handler {
    override fun handle(ctx: Context) {
        ctx.result("main.Dashboard")
    }
}
