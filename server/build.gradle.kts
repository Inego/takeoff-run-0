

plugins {
    val kotlinVersion = "1.3.61"
    kotlin("jvm") version kotlinVersion
    application
}

version = "0.0.1"




repositories {
    mavenCentral()
    jcenter()
}

object Versions {
    val ktor = "1.2.6"
    val log4j = "2.13.0"
    val jackson = "2.10.1"
}


dependencies {
    compile("org.kodein.di:kodein-di-generic-jvm:6.5.1")

    compile("io.ktor:ktor-server-core:${Versions.ktor}")
    compile("io.ktor:ktor-server-netty:${Versions.ktor}")
    compile("io.ktor:ktor-html-builder:${Versions.ktor}")

    compile("org.apache.logging.log4j:log4j-api:${Versions.log4j}")
    compile("org.apache.logging.log4j:log4j-core:${Versions.log4j}")
    compile("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")

    compile("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.jackson}")
    compile("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")

    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":common"))
}

application {
    mainClassName = "org.inego.takeoffrun.server.app.ServerApplicationKt.main"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}