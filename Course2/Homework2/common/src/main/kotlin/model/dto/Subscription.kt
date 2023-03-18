package model.dto

import org.ktorm.entity.Entity
import java.time.LocalDate

interface Subscription: Entity<Subscription> {
    companion object : Entity.Factory<Subscription>()

    val id: Int
    val until: LocalDate
    val client: Client
}