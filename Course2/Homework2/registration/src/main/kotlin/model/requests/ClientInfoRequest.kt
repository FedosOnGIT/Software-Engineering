package model.requests

import kotlinx.serialization.Serializable

@Serializable
data class ClientInfoRequest(val phone: String)