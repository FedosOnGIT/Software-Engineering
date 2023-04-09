package routing

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.database.Database
import service.ReportStatistics

fun Application.configureRouting(database: Database) {
    install(ContentNegotiation) {
        json()
    }

    val service = ReportStatistics(database = database)

    routing {
        post("/visits") {
            try {
                val response = service.dateStatistics()
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }
        post ("/average") {
            try {
                val response = service.avgStatistics()
                call.respond(HttpStatusCode.OK, response)
            } catch (exception: Exception) {
                call.respond(HttpStatusCode.BadRequest, exception.message!!)
            }
        }
    }
}