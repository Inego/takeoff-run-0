

plugins {
    val kotlinVersion = "1.4.32"
    kotlin("jvm") version kotlinVersion
    application
}

version = "0.0.1"




repositories {
    mavenCentral()
    jcenter()
}

object Versions {
    val ktor = "1.5.+"
    val log4j = "2.14.0"
    val jackson = "2.10.1"
}


dependencies {
    implementation("org.kodein.di:kodein-di-generic-jvm:6.5.1")

    implementation("io.ktor:ktor-server-core:${Versions.ktor}")
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")

    implementation("org.apache.logging.log4j:log4j-api:${Versions.log4j}")
    implementation("org.apache.logging.log4j:log4j-core:${Versions.log4j}")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.jackson}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")

    implementation("com.google.guava:guava:28.1-jre")

    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":common"))
}


tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}