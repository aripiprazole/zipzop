plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
}

val kotlinVersion = "1.4.0-rc"
val composeVersion = "0.1.0-dev13"
val roomVersion = "2.2.5"

android {
  buildToolsVersion = "30.0.1"
  compileSdkVersion(30)

  defaultConfig {
    applicationId = "com.lorenzoog.zipzop"
    versionCode = 1
    versionName = "1.0"

    minSdkVersion(23)
    targetSdkVersion(30)

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
//    release {
//      minifyEnabled = false
//      proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
//    }
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
      jvmTarget = "1.8"

      freeCompilerArgs = freeCompilerArgs + listOf("-Xuse-ir")
    }

    buildFeatures {
      compose = true
    }

    composeOptions {
      kotlinCompilerExtensionVersion = composeVersion
//      kotlinCompilerVersion = "1.3.70-dev-withExperimentalGoogleExtensions-20200424"
      kotlinCompilerVersion = kotlinVersion
    }
  }

  dependencies {
    // kotlin
    implementation(kotlin("stdlib", kotlinVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")

    // compose
    implementation("androidx.ui:ui-tooling:$composeVersion")
    implementation("androidx.ui:ui-layout:$composeVersion")
    implementation("androidx.ui:ui-material:$composeVersion")
    implementation("androidx.ui:ui-tooling:$composeVersion")

    // android
    implementation("androidx.appcompat:appcompat:1.3.0-alpha01")
    implementation("androidx.activity:activity-ktx:1.1.0")
    implementation("androidx.core:core-ktx:1.5.0-alpha01")
    implementation("com.google.android.material:material:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.0-alpha06")

    // database
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // testing
    testImplementation("junit:junit:4.13")
    testImplementation("androidx.room:room-testing:$roomVersion")

    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.ui:ui-test:$composeVersion")

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
  }
