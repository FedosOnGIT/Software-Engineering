package model.dto

import org.ktorm.entity.Entity

interface Client : Entity<Client> {
    companion object: Entity.Factory<Client>()
    val id: Int
    val name: String
    val phone: String
    val subscriptions: List<Subscription>
}