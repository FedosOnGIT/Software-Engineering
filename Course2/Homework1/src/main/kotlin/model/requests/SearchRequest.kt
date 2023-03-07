package model.requests

import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(val query: String, val timeout: Int, val number: Int)