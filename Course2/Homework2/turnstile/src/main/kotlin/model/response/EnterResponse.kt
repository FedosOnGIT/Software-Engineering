package model.response

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EnterResponse(val enterTime: LocalDateTime)