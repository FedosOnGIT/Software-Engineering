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
import utils.Constants

class GoogleActor(private val key: String, private val cx: String) : ChildActor() {

    override fun process(query: String, number: Int): MutableList<AnswerDto> {
        val client = HttpClient(CIO) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val answer = mutableListOf<AnswerDto>()
        runBlocking {
            client
                .get(Constants.GOOGLE_SEARCH_URL(key, cx, query, number))
                .body<GoogleAnswer>()
                .items
                .stream()
                .forEach {
                    answer.add(AnswerDto(it.title, it.link))
                }
        }
        return answer
    }
}