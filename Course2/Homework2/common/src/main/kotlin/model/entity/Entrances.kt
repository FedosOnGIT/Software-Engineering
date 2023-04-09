package model.entity

import model.dto.Entrance
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.datetime
import org.ktorm.schema.int

object Entrances : Table<Entrance>("entrances") {
    val subscriptionId = int("subscription_id").primaryKey().bindTo { it.subscriptionId }
    val enter = boolean("enter").primaryKey().bindTo { it.enter }
    val time = datetime("time").primaryKey().bindTo { it.time }
}