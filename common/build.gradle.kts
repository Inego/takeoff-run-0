plugins {
    kotlin("jvm") version "2.1.0" // Updated to the latest Kotlin version
}

group = "org.inego.takeoffrun"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:33.4.0-jre")
}

