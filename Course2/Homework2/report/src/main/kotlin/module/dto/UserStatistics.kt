package module.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserStatistics(val clientId: Int, val visits: Int, val averageTime: Long)
