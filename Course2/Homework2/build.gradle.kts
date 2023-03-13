import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
//    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
}

repositories {
    mavenCentral()
}

group = "org.example"
version = "1.0-SNAPSHOT"

val ktormVersion = "3.6.0"
ext["ktormVersion"] = ktormVersion

dependencies {
    implementation(platform("org.ktorm:ktorm-bom:$ktormVersion"))
    implementation("org.ktorm:ktorm-core")
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
