package org.inego.takeoffrun.server.app

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.nio.file.FileSystems
import java.security.SecureRandom
import java.util.*


fun main() {
    embeddedServer(Netty, port = 8080) {
        myKodeinApp()
    }.start(wait = true)
}

fun Application.myKodeinApp() = myKodeinApp(Kodein {
    bind<Random>() with singleton { SecureRandom() }
})

fun Application.myKodeinApp(kodein: Kodein) {

    val random by kodein.instance<Random>()

    routing {
        get("/") {
            val range = 0 until 100
            call.respondText("Random number in $range: ${random[range]}")
        }
    }

//    println(System.getProperty("user.dir"))
    FileSystems.getDefault()
}

private operator fun Random.get(range: IntRange) = range.first + this.nextInt(range.last - range.first)