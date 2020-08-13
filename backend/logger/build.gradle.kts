val logback_version: String by project

plugins {
  kotlin("jvm")
}

group = "com.lorenzoog.zipzop"
version = "0.0.1"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))

  implementation("ch.qos.logback:logback-classic:$logback_version")
  implementation("org.fusesource.jansi:jansi:1.18")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
