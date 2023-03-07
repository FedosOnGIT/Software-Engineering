import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.2.4"
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("org.jsoup:jsoup:1.15.4")
    implementation("com.typesafe.akka:akka-actor_2.11:2.4.17")
    implementation("org.slf4j:slf4j-api:1.7.25")
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-netty-jvm:2.2.4")
    implementation("io.ktor:ktor-server-core-jvm:2.2.4")
    implementation("ch.qos.logback:logback-classic:1.2.9")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("ActorsApplicationKT")
}