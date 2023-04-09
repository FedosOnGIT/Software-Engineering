package service

import kotlinx.datetime.toJavaLocalDateTime
import model.entity.Entrances
import model.event.TurnstileEvent
import org.ktorm.database.Database
import org.ktorm.dsl.insert

class EventService(private val database: Database) {
    fun logEvent(event: TurnstileEvent) {
        database.insert(Entrances) {
            set(it.subscriptionId, event.subscriptionId)
            set(it.enter, event.enter)
            set(it.time, event.time)
        }
    }
}