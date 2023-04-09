package service

import kotlinx.datetime.toKotlinLocalDateTime
import model.dto.Entrance
import model.dto.Subscription
import model.entity.ClientsSubscriptions
import model.entity.Entrances
import model.entity.Subscriptions
import model.event.TurnstileEvent
import model.request.EnterRequest
import model.request.OutRequest
import model.response.EnterResponse
import model.response.OutResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.time.LocalDateTime

class TurnstileService(private val database: Database) {

    private val eventService: EventService = EventService(database)

    private fun getSubscription(clientId: Int, subscriptionId: Int, time: LocalDateTime): Subscription? {
        val subscriptions = database.from(ClientsSubscriptions)
            .leftJoin(Subscriptions, on = ClientsSubscriptions.subscriptionId eq Subscriptions.id)
            .select(Subscriptions.id, Subscriptions.until)
            .where(
                condition = (ClientsSubscriptions.clientId eq clientId)
                        and (ClientsSubscriptions.subscriptionId eq subscriptionId)
                        and (Subscriptions.until gt time)
            )
            .map { row -> Subscriptions.createEntity(row) }
        return if (subscriptions.isEmpty())
            null
        else subscriptions[0]
    }

    private fun lastEntrance(subscriptionId: Int): Entrance? {
        val entrances = database.from(Entrances)
            .select(Entrances.subscriptionId, Entrances.enter, Entrances.time)
            .where(Entrances.subscriptionId eq subscriptionId)
            .orderBy(Entrances.time.desc())
            .limit(1)
            .map { row -> Entrances.createEntity(row) }
        return if (entrances.isEmpty()) null else entrances[0]
    }

    fun enter(request: EnterRequest): EnterResponse {
        val now = LocalDateTime.now()
        val subscriptionId = request.subscriptionId
        val clientId = request.clientId

        getSubscription(clientId, subscriptionId, now)
            ?: throw RuntimeException("No such active subscription $subscriptionId for client $clientId")

        val last = lastEntrance(subscriptionId)

        if (last != null && last.enter) {
            throw RuntimeException("Client with id $clientId has already entered")
        }

        eventService.logEvent(
            TurnstileEvent(
                subscriptionId = subscriptionId,
                enter = true,
                time = now
            )
        )

        return EnterResponse(enterTime = now.toKotlinLocalDateTime())
    }

    fun out(request: OutRequest): OutResponse {
        val now = LocalDateTime.now()
        val subscriptionId = request.subscriptionId
        val clientId = request.clientId

        val last = lastEntrance(subscriptionId)

        if (last == null || !last.enter) {
            throw RuntimeException("Client with id $clientId hasn't entered")
        }

        eventService.logEvent(
            TurnstileEvent(
                subscriptionId = subscriptionId,
                enter = false,
                time = now
            )
        )

        return OutResponse(outTime = now.toKotlinLocalDateTime())
    }
}