import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    //kotlin("multiplatform") version "1.6.10"
    application
}

group = "me.diamo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Arrow-kt
    implementation("io.arrow-kt:arrow-core:1.0.6-alpha.1")
    implementation("io.arrow-kt:arrow-optics:1.0.6-alpha.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.0.6-alpha.1")
    implementation("io.arrow-kt:arrow-fx-stm:1.0.6-alpha.1")
    // Command line arguments parser
    implementation("com.github.ajalt.clikt:clikt:3.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}