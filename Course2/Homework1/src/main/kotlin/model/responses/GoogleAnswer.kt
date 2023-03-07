package model.responses

import kotlinx.serialization.Serializable
import model.dto.GoogleItemDto

@Serializable
data class GoogleAnswer(val items: MutableList<GoogleItemDto>)