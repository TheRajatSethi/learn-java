package main

import io.javalin.http.Context
import io.javalin.http.Handler
import java.io.StringWriter

class Index : Handler{
    override fun handle(ctx: Context) {

        val w = StringWriter()

//        App.compiledIndexTemplate.evaluate(w, mapOf("user" to "user"))
        App.compiledIndexTemplate.evaluateBlock("content", w, mapOf("user" to "user"))

        ctx.result(w.toString())
        ctx.header("content-type", "text/html; charset=utf-8")
//        ctx.render("templates/index.peb")
//        ctx.ren
    }
}

class About : Handler{
    override fun handle(ctx: Context) {
//        val sampleUser = User(1, "John")

        val w = StringWriter()

//        App.compiledIndexTemplate.evaluate(w, mapOf("user" to sampleUser))

        println()
//        ctx.render("templates/index.html", mapOf("user" to sampleUser))
    }
}



class Contact : Handler{
    override fun handle(ctx: Context) {

        val times = ctx.formParam("times")
        val int_times = times?.toInt()?:0



//        test(int_times)
        ctx.result("main.Contact")
    }
}