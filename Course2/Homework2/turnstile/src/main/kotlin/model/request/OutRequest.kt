package model.request

import kotlinx.serialization.Serializable

@Serializable
data class OutRequest(val clientId: Int, val subscriptionId: Int)
