package routing

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import org.ktorm.database.Database

fun Application.configureRouting(database: Database) {
    install(ContentNegotiation) {
        json()
    }

//    routing {
//        post("/enter") {
//            val request = call.receive<EnterRequest>()
//            database.insert()
//        }
//    }
}