package model.entity

import model.dto.Subscription
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.datetime
import org.ktorm.schema.int

object Subscriptions : Table<Subscription>("subscriptions") {
    val id = int("id").primaryKey().bindTo { it.id }
    val until = datetime("until").bindTo { it.until }
}