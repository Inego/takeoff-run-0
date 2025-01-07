plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.inego.takeoffrun"
version = "0.0.1"

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.3"
val kodeinVersion = "7.23.1"
val log4jVersion = "2.20.0"

dependencies {
    implementation(project(":common"))
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("org.kodein.di:kodein-di:$kodeinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.3")
}
