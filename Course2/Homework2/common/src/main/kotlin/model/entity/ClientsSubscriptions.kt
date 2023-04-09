package model.entity

import model.dto.ClientSubscription
import org.ktorm.schema.Table
import org.ktorm.schema.int

object ClientsSubscriptions : Table<ClientSubscription>("clients_subscriptions") {
    val clientId = int("client_id").primaryKey().bindTo { it.clientId }
    val subscriptionId = int("subscription_id").primaryKey().bindTo { it.subscriptionId }
}