plugins {
    kotlin("multiplatform") version "1.6.20-RC"
}

group = "me.diamo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "grek.main"
            }
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                // Native IO
                implementation("com.squareup.okio:okio:3.0.0")
                // Arrow-kt
                implementation("io.arrow-kt:arrow-core:1.0.6-alpha.1")
                implementation("io.arrow-kt:arrow-optics:1.0.6-alpha.1")
                implementation("io.arrow-kt:arrow-fx-coroutines:1.0.6-alpha.1")
                implementation("io.arrow-kt:arrow-fx-stm:1.0.6-alpha.1")
                // Command line arguments parser
                implementation("com.github.ajalt.clikt:clikt:3.4.0")
            }
        }
        val nativeTest by getting

    }
}
