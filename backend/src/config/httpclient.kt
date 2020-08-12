package com.lorenzoog.zipzop.config

import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun Application.setupHttpClient() {
  val client = HttpClient(CIO) {
    install(HttpTimeout) {
    }

    install(Auth) {
    }

    install(JsonFeature) {
      serializer = GsonSerializer()
    }

    install(Logging) {
      level = LogLevel.HEADERS
    }

    // install(UserAgent) { agent = "some user agent" }
  }
}
