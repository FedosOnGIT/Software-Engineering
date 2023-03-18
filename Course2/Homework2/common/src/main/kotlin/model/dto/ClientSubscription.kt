package model.dto

import org.ktorm.entity.Entity

interface ClientSubscription : Entity<ClientSubscription> {
    companion object : Entity.Factory<ClientSubscription>()

    val clientId: Int
    val subscriptionId: Int
}