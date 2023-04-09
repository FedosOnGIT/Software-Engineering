package routing

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.request.EnterRequest
import model.request.OutRequest
import org.ktorm.database.Database
import service.TurnstileService

fun Application.configureRouting(database: Database) {
    install(ContentNegotiation) {
        json()
    }

    val turnstile = TurnstileService(database)

    routing {
        post("/enter") {
            try {
                val request = call.receive<EnterRequest>()
                val response = turnstile.enter(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }
        post("/out") {
            try {
                val request = call.receive<OutRequest>()
                val response = turnstile.out(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }
    }
}