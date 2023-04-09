package model.dto

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Entrance : Entity<Entrance> {
    companion object : Entity.Factory<Entrance>()

    val subscriptionId : Int
    val enter: Boolean
    val time: LocalDateTime
}