package module.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DateStatistics(val date: LocalDate, val entrances: Int)