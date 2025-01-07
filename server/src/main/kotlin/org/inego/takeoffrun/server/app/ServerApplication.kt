package org.inego.takeoffrun.server.app

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import java.nio.file.FileSystems
import java.security.SecureRandom
import java.util.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        myKodeinApp()
    }.start(wait = true)
}

fun Application.myKodeinApp() = myKodeinApp(DI {
    bind<Random>() with singleton { SecureRandom() }
})

fun Application.myKodeinApp(kodein: DI) {
    val random by kodein.instance<Random>()

    routing {
        get("/") {
            val range = 0 until 100
            call.respondText("Random number in $range: ${random[range]}")
        }
    }

    FileSystems.getDefault()
}

private operator fun Random.get(range: IntRange) = range.first + this.nextInt(range.last - range.first)