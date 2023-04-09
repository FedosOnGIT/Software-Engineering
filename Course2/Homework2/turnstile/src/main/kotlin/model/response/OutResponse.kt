package model.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class OutResponse(val outTime: LocalDateTime)
