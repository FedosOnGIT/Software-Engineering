import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database
import org.ktorm.support.postgresql.PostgreSqlDialect
import routing.configureRouting

fun main() {
    val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/fitness",
        user = "homework2",
        dialect = PostgreSqlDialect()
    )
    embeddedServer(Netty, port = 8081) {
        configureRouting(database)
    }.start(wait = true)
}