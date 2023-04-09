package model.entity

import model.dto.Client
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Clients : Table<Client>("clients") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val phone = varchar("phone").bindTo { it.phone }
}