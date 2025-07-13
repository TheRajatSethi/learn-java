package main

import io.javalin.http.Context
import io.javalin.http.Handler
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate


/**
 * ```
 * Reference Data
 *
 */

enum class OpenID(val description: String) {
    NA("NA"),
    GOOGLE("Google"),
}

/**
 * ```java
 * Models
 * ```
 */

data class AccountModel(
    var id: Int = 0,
    var email: String = "",
    var password: String = "",
    var openID: OpenID = OpenID.NA,
    var createdAt: Instant = Clock.System.now()
)


/**
 * ```
 * DAO Interface
 */

interface JDBIAccountRepository {
    @SqlQuery("select * from account")
    fun all(): List<AccountModel>

    @SqlUpdate("Insert into account (email, password, openID) values (:account.email, :account.password, :account.openID)")
    @GetGeneratedKeys
    fun create(account: AccountModel): Int

    @SqlQuery("Select * from account where id = :id")
    fun getById(id: Int): AccountModel?

    @SqlQuery("Select * from account where email = :email")
    fun getByEmail(email: String): AccountModel?
}


/**
 * ```
 * Repository Methods
 */

class AccountRepository {
    companion object {

        fun accountCreate(a: AccountModel): AccountModel {
            val id = App.jdbi.withExtensionUnchecked(JDBIAccountRepository::class.java) { it -> it.create(a) }
            a.id = id
            return a
        }

        fun accountGetById(id: Int): AccountModel? {
            val a = App.jdbi.withExtensionUnchecked(JDBIAccountRepository::class.java) { it.getById(id) }
            return a
        }

        fun accountGetByEmail(email: String): AccountModel? {
            val a = App.jdbi.withExtensionUnchecked(JDBIAccountRepository::class.java) { it -> it.getByEmail(email) }
            return a
        }
    }
}


/**
 * ```
 * Views & Handler
 */


class SignUpGetHandler : Handler {
    override fun handle(ctx: Context) {
        ctx.result("SignUp")
    }
}


class SignUpPostHandler : Handler {
    override fun handle(ctx: Context) {
        val email = ctx.formParam("email") ?: ""
        val password1 = ctx.formParam("password1") ?: ""
        val password2 = ctx.formParam("password2") ?: ""

        val errors = mutableMapOf<String, String>()

        if (email == "") errors["email"] = "Invalid Email"
        if (password1 == "" || password1 != password2) errors["password2"] = "Invalid Password(s) / Do not match"
        if (errors.isNotEmpty()) return

        val a = AccountRepository.accountGetByEmail(email)

        if (a != null) {
            errors["general"] = "Account already exists with this combination"
            ctx.result(errors.toString())
            return
        } else {
            val id = AccountRepository.accountCreate(
                AccountModel(
                    email = email,
                    password = password1,
                    openID = OpenID.GOOGLE
                )
            )
            ctx.result("account created with id $id")
        }

        ctx.result(errors.toString())
    }
}

class SignInGetHandler : Handler {
    override fun handle(ctx: Context) {
        ctx.result("SignIn")
    }
}


class SignInPostHandler : Handler {
    override fun handle(ctx: Context) {
        val email = ctx.formParam("email") ?: ""
        val password = ctx.formParam("password") ?: ""

        val errors = mutableMapOf<String, String>()

        if (email == "" || password == "") {
            errors["general"] = "Invalid Email or Password Combination"
            ctx.result(errors.toString())
            return
        }

        val a = AccountRepository.accountGetByEmail(email)

        when {
            a == null -> errors["general"] = "Invalid Email or Password Combination"
            a.password != password -> errors["general"] = "Invalid Email or Password Combination"
            else -> ctx.result("login successful")
        }

        when {
            errors.isEmpty() -> ctx.result("login successful")
            else -> ctx.result(errors.toString())
        }
    }
}
