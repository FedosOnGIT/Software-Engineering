package model.request;

import kotlinx.serialization.Serializable;

@Serializable
data class EnterRequest(val clientId: Int, val subscriptionId: Int)
