rootProject.name = "Homework2"
include("turnstile")
include("homework-db")
include("common")
include("registration")
include("report")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            //region versions
            version("ktorm", "3.6.0")
            version("ktor", "2.2.4")
            version("postgres", "42.5.4")
            version("kotlinx-datetime", "0.4.0")
            version("logback", "1.2.9")
            //endregion
            //region ktorm
            library("ktorm-core", "org.ktorm", "ktorm-core").versionRef("ktorm")
            library("ktorm-postgres", "org.ktorm", "ktorm-support-postgresql").versionRef("ktorm")
            bundle("ktorm", listOf("ktorm-core", "ktorm-postgres"))
            //endregion
            //region ktor
            library("ktor-content", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-netty", "io.ktor", "ktor-server-netty-jvm").versionRef("ktor")
            library("ktor-serialization", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            bundle("ktor", listOf("ktor-content", "ktor-netty", "ktor-serialization"))
            //endregion
            library("postgres", "org.postgresql", "postgresql").versionRef("postgres")
            library("kotlinx-datetime","org.jetbrains.kotlinx","kotlinx-datetime").versionRef("kotlinx-datetime")
            library("logback", "ch.qos.logback", "logback-classic").versionRef("logback")
        }
    }
}
include("report")
