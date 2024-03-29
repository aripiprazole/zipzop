import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val koin_version: String by project

plugins {
  application
  kotlin("jvm") version "1.3.70"
  kotlin("plugin.serialization") version "1.3.70"
}

group = "me.devgabi"
version = "0.0.1"

application {
  mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
  mavenLocal()
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
  maven { url = uri("https://kotlin.bintray.com/kotlinx") }
}

dependencies {
  implementation(project(":logger"))

  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
  implementation("io.ktor:ktor-server-netty:$ktor_version")
  implementation("io.ktor:ktor-server-core:$ktor_version")
  implementation("io.ktor:ktor-locations:$ktor_version")
  implementation("io.ktor:ktor-server-host-common:$ktor_version")
  implementation("io.ktor:ktor-auth:$ktor_version")
  implementation("io.ktor:ktor-auth-jwt:$ktor_version")
  implementation("io.ktor:ktor-client-core:$ktor_version")
  implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
//  implementation("io.ktor:ktor-client-http-timeout:$ktor_version")
  implementation("io.ktor:ktor-auth-jwt:$ktor_version")
  implementation("io.ktor:ktor-client-auth-jvm:$ktor_version")
  implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
  implementation("io.ktor:ktor-client-gson:$ktor_version")
  implementation("io.ktor:ktor-client-cio:$ktor_version")
  implementation("io.ktor:ktor-websockets:$ktor_version")
  implementation("io.ktor:ktor-client-websockets:$ktor_version")
  implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
  implementation("io.ktor:ktor-serialization:$ktor_version")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")

  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

  implementation("de.mkammerer:argon2-jvm:2.7")

  implementation("org.koin:koin-core:$koin_version")
  implementation("org.koin:koin-ktor:$koin_version")

  runtimeOnly("com.h2database:h2:1.4.200") // TODO: remove this, just for local environment

  testImplementation("io.ktor:ktor-server-tests:$ktor_version")
  testImplementation("org.koin:koin-test:$koin_version")
}

tasks.withType<KotlinCompile>() {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    jvmTarget = "1.8"
  }
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
