package module.response

import kotlinx.serialization.Serializable
import module.dto.DateStatistics

@Serializable
data class DateStatisticsResponse(val statistics: List<DateStatistics>)