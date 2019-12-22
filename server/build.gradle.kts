

plugins {
    val kotlinVersion = "1.3.61"
    kotlin("jvm") version kotlinVersion
    application
}

version = "0.0.1"

val ktorVersion = "1.2.6"


repositories {
    mavenCentral()
    jcenter()
}


dependencies {
    compile("org.kodein.di:kodein-di-generic-jvm:6.5.1")
    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-html-builder:$ktorVersion")
    compile("org.snakeyaml:snakeyaml-engine:2.0")
    compile("org.apache.logging.log4j:log4j-api:2.13.0")
    compile("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
    compile("org.apache.logging.log4j:log4j-core:2.13.0")
//    compile("ch.qos.logback:logback-classic:1.2.3")
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