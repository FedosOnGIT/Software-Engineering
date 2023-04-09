package routing

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.requests.ClientInfoRequest
import model.requests.RegistrationRequest
import model.requests.SubscriptionRequest
import org.ktorm.database.Database
import service.RegistrationService

fun Application.configureRouting(database: Database) {
    install(ContentNegotiation) {
        json()
    }

    val service = RegistrationService(database = database)

    routing {
        post("/register") {
            try {
                val request = call.receive<RegistrationRequest>()
                val response = service.registerClient(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }

        post("/subscribe") {
            try {
                val request = call.receive<SubscriptionRequest>()
                val response = service.subscribeClient(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }

        get("/info") {
            try {
                val request = call.receive<ClientInfoRequest>()
                val response = service.getClientInfo(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }
    }
}