package rounting

import actors.MainActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.message.AnswerMessage
import model.message.SearchMessage
import model.requests.SearchRequest
import model.responses.SearchResponse
import java.util.concurrent.CompletableFuture

fun Application.configureRouting(googleUrl: String, duckDuckGoUrl: String) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/search") {
            val request = call.receive<SearchRequest>()
            val system = ActorSystem.create("search")

            val main = system.actorOf(
                Props.create(MainActor::class.java, googleUrl, duckDuckGoUrl),
                "main"
            )

            val result = CompletableFuture<AnswerMessage>()

            main.tell(
                SearchMessage(request.query, request.timeout, request.number, result),
                ActorRef.noSender()
            )

            val answer = withContext(Dispatchers.IO) {
                result.get()
            }

            if (answer is AnswerMessage) {
                call.respond(HttpStatusCode.OK, SearchResponse(answer.answers))
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}