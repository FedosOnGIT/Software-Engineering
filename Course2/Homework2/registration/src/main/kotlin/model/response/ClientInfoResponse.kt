package model.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ClientInfoResponse(
    val clientId: Int,
    val name: String,
    val phone: String,
    val subscriptionId: Int?,
    val expiresAt: LocalDateTime?
)