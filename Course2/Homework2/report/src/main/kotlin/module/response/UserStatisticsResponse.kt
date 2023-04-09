package module.response

import kotlinx.serialization.Serializable
import module.dto.UserStatistics

@Serializable
data class UserStatisticsResponse(val statistics: List<UserStatistics>)