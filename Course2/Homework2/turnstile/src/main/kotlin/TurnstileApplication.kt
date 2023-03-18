import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database
import routing.configureRouting

fun main() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        user = "homework2"
    )
    embeddedServer(Netty, port = 8080) {
        configureRouting(database)
    }.start(wait = true)
}