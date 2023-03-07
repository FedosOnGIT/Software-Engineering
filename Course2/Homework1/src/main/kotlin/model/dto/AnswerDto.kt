package model.dto

import kotlinx.serialization.Serializable

@Serializable
data class AnswerDto(val title: String, val href: String)