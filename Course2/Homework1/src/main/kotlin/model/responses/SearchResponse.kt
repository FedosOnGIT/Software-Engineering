package model.responses

import kotlinx.serialization.Serializable
import model.dto.AnswerDto

@Serializable
data class SearchResponse(
    val answers: List<AnswerDto>
)