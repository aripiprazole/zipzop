package com.lorenzoog

import com.lorenzoog.zipzop.module
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.createTestEnvironment
import io.ktor.server.testing.withApplication
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.ModuleDeclaration
import org.koin.ktor.ext.modules

@OptIn(KtorExperimentalAPI::class)
fun withMocksApplication(mocks: ModuleDeclaration): (TestApplicationEngine.() -> Unit) -> Unit = { block ->
  withApplication(createTestEnvironment {
    config = HoconApplicationConfig(ConfigFactory.load("test-application"))

    module {
      module()

      modules(org.koin.dsl.module(moduleDeclaration = mocks))
    }
  }) {
    block()
  }
}
