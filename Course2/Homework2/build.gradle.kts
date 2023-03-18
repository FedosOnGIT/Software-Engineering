import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
}

repositories {
    mavenCentral()
}

group = "org.example"
version = "1.0-SNAPSHOT"

val ktormVersion = "3.6.0"
val ktorVersion = "2.2.4"
ext["ktormVersion"] = ktormVersion
ext["ktorVersion"] = ktorVersion

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
        languageVersion = "1.5"
        apiVersion = "1.5"
    }
}
