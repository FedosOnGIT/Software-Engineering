package model.dto

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Subscription : Entity<Subscription> {
    companion object : Entity.Factory<Subscription>()

    val id: Int
    val until: LocalDateTime
    val client: Client
}