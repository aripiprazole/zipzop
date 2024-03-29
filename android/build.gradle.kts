// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
  }

  val kotlinVersion: String by project

  dependencies {
    classpath("com.android.tools.build:gradle:4.2.0-alpha07")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven("https://jitpack.io")
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
  }
}
