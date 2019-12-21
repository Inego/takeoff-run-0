plugins {
    kotlin("jvm") version "1.3.61"
}

version = "0.0.1"

val ktorVersion = "1.2.6"


repositories {
    mavenCentral()
    jcenter()
}


dependencies {
    compile("com.google.inject:guice:4.2.2")
    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
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