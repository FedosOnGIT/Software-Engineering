package model.message

import java.util.concurrent.CompletableFuture

data class SearchMessage(
    val query: String,
    val timeout: Int,
    val number: Int,
    val result: CompletableFuture<AnswerMessage>
)