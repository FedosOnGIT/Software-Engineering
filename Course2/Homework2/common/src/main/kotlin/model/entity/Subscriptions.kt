package model.entity

import model.dto.Subscription
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int

object Subscriptions : Table<Subscription>("subscription") {
    val id = int("id").bindTo { it.id }
    val until = date("until").bindTo { it.until }
    val clientId = int("client_id").referenceTable
}