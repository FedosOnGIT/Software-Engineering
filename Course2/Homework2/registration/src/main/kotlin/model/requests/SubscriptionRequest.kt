package model.requests

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionRequest(
    val clientId: Int,
    val until: LocalDateTime
)
