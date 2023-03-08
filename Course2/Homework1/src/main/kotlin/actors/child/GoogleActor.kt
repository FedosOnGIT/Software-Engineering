package actors.child

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import model.dto.AnswerDto
import model.responses.GoogleAnswer

class GoogleActor(url: String) : ChildActor(url) {

    override fun process(query: String, number: Int): MutableList<AnswerDto> {
        val client = HttpClient(CIO) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        return runBlocking {
            client
                .get(
                    url +
                            "&q=$query" +
                            "&num=$number"
                )
                .body<GoogleAnswer>()
                .items
        }.stream()
            .map { AnswerDto(it.title, it.link) }
            .toList()
    }
}