package rs

import kotlinx.datetime.Instant
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.sql.DriverManager
import java.sql.Types

data class Person(val id: Int, var name: String){

}

object AppDap{
    val con = DriverManager.getConnection("jdbc:sqlite:db.db")!!
    val jdbi = Jdbi.create(con).installPlugins()!!
}

interface JDBISessionRepository2 {
    @SqlUpdate("Insert into Person (id, name) values (:a.id, :a.name);")
    fun create(a: Person)

    @SqlUpdate("Insert into SomeTable (a,b) values (:a, :b);")
    fun createInB(a: Boolean, b: Boolean)
}



fun main(){
//    App.jdbi.withExtensionUnchecked(JDBISessionRepository::class.java) {it -> it.create(Person(1,"Sam"))}
    AppDap.jdbi.withExtensionUnchecked(JDBISessionRepository2::class.java) {it -> it.createInB(true, false)}
}
