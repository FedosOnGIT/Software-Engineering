package model.dto

import org.ktorm.entity.Entity
import java.time.ZonedDateTime

interface Subscription: Entity<Subscription> {
    companion object : Entity.Factory<Subscription>()
    val id: Int
    val until: ZonedDateTime
}