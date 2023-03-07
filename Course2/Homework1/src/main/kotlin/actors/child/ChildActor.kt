package actors.child

import akka.actor.UntypedActor
import model.dto.AnswerDto
import model.exceptions.NoSuchMessageException
import model.message.AnswerMessage
import model.message.BrowseMessage

abstract class ChildActor : UntypedActor() {
    abstract fun process(query: String, number: Int): MutableList<AnswerDto>

    override fun onReceive(message: Any?) {
        if (message is BrowseMessage) {
            val answer = AnswerMessage(process(query = message.query, number = message.number))
            sender.tell(answer, self())
            context.stop(self())
        } else {
            throw NoSuchMessageException()
        }
    }
}