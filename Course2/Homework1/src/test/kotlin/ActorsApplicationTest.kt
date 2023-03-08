import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import model.requests.SearchRequest
import model.responses.SearchResponse
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.Test
import rounting.configureRouting
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertEquals


class ActorsApplicationTest {


    private val webServer: MockWebServer = MockWebServer()


    private fun createDispatcher(googleTimeout: Long, duckDuckGoTimeout: Long): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                assert(request.path != null)
                return if (request.path!!.startsWith("/google")) {
                    Thread.sleep(googleTimeout)
                    MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-Type", "application/json")
                        .setBody(Files.readString(Path.of("src/test/resources/google.json")))
                } else if (request.path!!.startsWith("/duckDuckGo")) {
                    Thread.sleep(duckDuckGoTimeout)
                    MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-Type", "application/json")
                        .setBody(Files.readString(Path.of("src/test/resources/duckDuckGo.xml")))
                } else {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    @Test
    fun allWorkingTest() = testApplication {
        val url: String = webServer.url("/").toString()
        webServer.dispatcher = createDispatcher(0, 0)

        application {
            configureRouting(url + "google", url + "duckDuckGo")
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response: SearchResponse = client.get("/search") {
            header("Content-Type", ContentType.Application.Json.toString())
            setBody(SearchRequest("test", 5, 5))
        }.body()

        assertEquals(10, response.answers.size)
    }

    @Test
    fun oneTimeoutTest() = testApplication {
        val url: String = webServer.url("/").toString()
        webServer.dispatcher = createDispatcher(2000, 0)

        application {
            configureRouting(url + "google", url + "duckDuckGo")
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response: SearchResponse = client.get("/search") {
            header("Content-Type", ContentType.Application.Json.toString())
            setBody(SearchRequest("test", 1, 2))
        }.body()

        assertEquals(2, response.answers.size)
    }

    @Test
    fun allTimoutTest() = testApplication {
        val url: String = webServer.url("/").toString()
        webServer.dispatcher = createDispatcher(2000, 2000)

        application {
            configureRouting(url + "google", url + "duckDuckGo")
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response: SearchResponse = client.get("/search") {
            header("Content-Type", ContentType.Application.Json.toString())
            setBody(SearchRequest("test", 1, 2))
        }.body()

        assertEquals(0, response.answers.size)
    }
}