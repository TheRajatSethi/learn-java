package rs

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.UUID

interface JDBISessionRepository {
//    @SqlQuery("select * from session where id = :id")
//    fun getById(id: UUID): SessionModel?
//
//    @SqlUpdate("Insert into session (id, userId, lastAccessedAt) values (:id, :userId, :lastAccessedAt)") // FIXME
//    fun create(account: SessionModel): Unit
//
//    @SqlQuery("delete from session where id = :id")
//    fun deleteById(id: String): Unit

    @SqlUpdate("Insert into A (a, b) values (1, :a);")
    fun create(a: Int)

    @SqlUpdate("Insert into SomeTable (id, name) values (:st.id, :st.name);")
    fun createIntoSomeTable(st: SomeTable)
}

data class SomeTable(
    var id: Int,
    var name: String,
){}
