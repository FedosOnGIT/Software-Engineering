package model.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(val name: String, val phone: String)