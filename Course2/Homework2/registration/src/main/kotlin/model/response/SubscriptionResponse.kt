package model.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionResponse(
    val clientId: Int,
    val subscriptionId: Int,
    val until: LocalDateTime
)