package com.lorenzoog

import com.lorenzoog.zipzop.module
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.createTestEnvironment
import io.ktor.server.testing.withApplication
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.ktor.ext.modules

@OptIn(KtorExperimentalAPI::class)
fun createTestEnvironment(mocks: ModuleDeclaration) = createTestEnvironment {
  config = HoconApplicationConfig(ConfigFactory.load("test-application"))

  module {
    module()

    modules(module(moduleDeclaration = mocks))
  }
}

@OptIn(KtorExperimentalAPI::class)
fun withMocksApplication(mocks: ModuleDeclaration = {}): (TestApplicationEngine.() -> Unit) -> Unit = { block ->
  withApplication(createTestEnvironment(mocks)) {
    block()
  }
}

@OptIn(KtorExperimentalAPI::class)
fun createApplicationsWithMocks(mocks: ModuleDeclaration = {}): ApplicationEngineEnvironment {
  return applicationEngineEnvironment {
    config = HoconApplicationConfig(ConfigFactory.load("test-application"))

    module {
      module()

      modules(module(moduleDeclaration = mocks))
    }
  }
}
