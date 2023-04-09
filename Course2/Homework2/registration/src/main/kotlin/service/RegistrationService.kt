package service

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import model.entity.Clients
import model.entity.ClientsSubscriptions
import model.entity.Subscriptions
import model.requests.ClientInfoRequest
import model.requests.RegistrationRequest
import model.requests.SubscriptionRequest
import model.response.ClientInfoResponse
import model.response.RegistrationResponse
import model.response.SubscriptionResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*

class RegistrationService(val database: Database) {

    fun registerClient(request: RegistrationRequest): RegistrationResponse {
        val id = database.insertAndGenerateKey(Clients) {
            set(it.name, request.name)
            set(it.phone, request.phone)
        }

        if (id is Int) {
            return RegistrationResponse(id)
        } else {
            throw RuntimeException("Incorrect id")
        }
    }

    fun subscribeClient(request: SubscriptionRequest): SubscriptionResponse {
        var until = request.until.toJavaLocalDateTime()
        val now = java.time.LocalDateTime.now()

        if (until < now) {
            throw RuntimeException("Subscription is expired")
        }

        val last = database.from(ClientsSubscriptions)
            .innerJoin(Subscriptions, on = Subscriptions.id eq ClientsSubscriptions.subscriptionId)
            .select(Subscriptions.id, Subscriptions.until, ClientsSubscriptions.clientId)
            .where((ClientsSubscriptions.clientId eq request.clientId) and (Subscriptions.until gt now))
            .map { row ->
                Subscriptions.createEntity(row)
            }

        if (last.isEmpty()) {
            val subscriptionId = database.useTransaction {
                val subscriptionId = database.insertAndGenerateKey(Subscriptions) {
                    set(it.until, until)
                }
                if (subscriptionId is Int) {
                    database.insert(ClientsSubscriptions) {
                        set(it.clientId, request.clientId)
                        set(it.subscriptionId, subscriptionId)
                    }
                    subscriptionId
                } else {
                    throw RuntimeException("Can't insert subscription")
                }
            }
            return SubscriptionResponse(
                clientId = request.clientId,
                subscriptionId = subscriptionId,
                until = request.until
            )
        } else if (last.size == 1) {
            val subscription = last[0]

            if (until > subscription.until) {
                database.update(Subscriptions) {
                    set(it.until, until)
                    where {
                        it.id eq subscription.id
                    }
                }
            } else {
                until = subscription.until
            }
            return SubscriptionResponse(
                clientId = request.clientId,
                subscriptionId = subscription.id,
                until = until.toKotlinLocalDateTime()
            )
        } else {
            throw RuntimeException("More that one active subscription")
        }
    }

    fun getClientInfo(request: ClientInfoRequest): ClientInfoResponse {
        val now = java.time.LocalDateTime.now()
        val phone = request.phone
        val clients = database.from(Clients)
            .leftJoin(ClientsSubscriptions, on = Clients.id eq ClientsSubscriptions.clientId)
            .leftJoin(Subscriptions, on = (ClientsSubscriptions.subscriptionId eq Subscriptions.id))
            .selectDistinct(Clients.id, Clients.name, Clients.phone, Subscriptions.id, Subscriptions.until)
            .where(Clients.phone eq phone)
            .orderBy(Subscriptions.until.desc())
            .limit(1)
            .map { row ->
                var until = row[Subscriptions.until]
                if (until != null && until < now) {
                    until = null
                }
                ClientInfoResponse(
                    clientId = row[Clients.id]!!,
                    name = row[Clients.name]!!,
                    phone = row[Clients.phone]!!,
                    subscriptionId = if (until == null) null else row[Subscriptions.id],
                    expiresAt = until?.toKotlinLocalDateTime()
                )
            }

        if (clients.isEmpty()) {
            throw RuntimeException("No client which is assigned to $phone")
        } else {
            return clients.first()
        }
    }
}