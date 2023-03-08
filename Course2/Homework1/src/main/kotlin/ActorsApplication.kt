import io.ktor.server.engine.*
import io.ktor.server.netty.*
import rounting.configureRouting

fun main(args: Array<String>) {
    if (args.size != 2) {
        throw IllegalArgumentException("Need google key and google cx!")
    }
    val googleKey = args[0]
    val googleCx = args[1]
    embeddedServer(Netty, port = 8000) {
        configureRouting(googleKey, googleCx)
    }.start(wait = true)
}