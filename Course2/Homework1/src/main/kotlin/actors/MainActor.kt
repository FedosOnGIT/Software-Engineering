package actors

import actors.child.DuckDuckGoActor
import actors.child.GoogleActor
import akka.actor.*
import akka.japi.pf.DeciderBuilder
import model.dto.AnswerDto
import model.exceptions.NoSuchMessageException
import model.message.AnswerMessage
import model.message.BrowseMessage
import model.message.SearchMessage
import scala.concurrent.duration.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


class MainActor(private val googleKey: String, private val googleCx: String) : UntypedActor() {
    private val children = 2
    private val result = mutableListOf<AnswerDto>()
    private var received = 0
    private lateinit var answer: CompletableFuture<AnswerMessage>

    private fun send() {
        answer.complete(AnswerMessage(result))
        context.stop(self())
    }

    override fun supervisorStrategy(): SupervisorStrategy {
        return OneForOneStrategy(
            false, DeciderBuilder
                .match(
                    NoSuchMessageException::class.java
                ) { OneForOneStrategy.restart() }
                .match(
                    ReceiveTimeout::class.java
                ) { OneForOneStrategy.stop() }
                .build()
        )
    }

    override fun onReceive(message: Any?) {
        if (message is SearchMessage) {
            answer = message.result
            val browseMessage = BrowseMessage(message.query, message.number)

            val googleActor = context.actorOf(
                Props.create(
                    GoogleActor::class.java,
                    googleKey, googleCx
                ),
                "google"
            )
            val duckActor = context.actorOf(
                Props.create(
                    DuckDuckGoActor::class.java
                ),
                "duck"
            )

            googleActor.tell(browseMessage, self())
            duckActor.tell(browseMessage, self())

            context.setReceiveTimeout(Duration.create(message.timeout.toLong(), TimeUnit.SECONDS))
        } else if (message is AnswerMessage) {
            result.addAll(message.answers)
            received++
            if (received == children) {
                send()
            }
        } else if (message is ReceiveTimeout) {
            send()
        } else {
            throw NoSuchMessageException()
        }
    }
}