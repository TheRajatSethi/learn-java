package main

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*


/**
 * ```
 * Models
 *
 */

data class SessionModel(
    var id: UUID = UUID.randomUUID(),
    var userId: Int = 0,
    var createdAt: Instant = Clock.System.now(),
    var lastAccessedAt: Instant = Clock.System.now()
)


/**
 * ```
 * JDBI Repository
 *
 */

interface JDBISessionRepository {
    @SqlQuery("select * from session where id = :id")
    fun getById(id: UUID): SessionModel?

    @SqlUpdate("Insert into session (id, userId, lastAccessedAt) values (:id, :userId, :lastAccessedAt)") // FIXME
    fun create(account: SessionModel): Unit

    @SqlQuery("delete from session where id = :id")
    fun deleteById(id: String): Unit
}


class SessionRepository {
    companion object {

        fun getById(id: UUID): SessionModel? {
            TODO("Not yet implemented")
        }

        fun create(account: SessionModel) {
            TODO("Not yet implemented")
        }

        fun deleteById(id: String) {
            TODO("Not yet implemented")
        }

    }
}

