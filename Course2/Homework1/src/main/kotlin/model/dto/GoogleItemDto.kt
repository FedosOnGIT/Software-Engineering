package model.dto

import kotlinx.serialization.Serializable

@Serializable
data class GoogleItemDto(val title: String, val link: String)