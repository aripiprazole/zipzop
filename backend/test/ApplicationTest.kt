package com.lorenzoog

import com.lorenzoog.zipzop.Json
import com.lorenzoog.zipzop.config.auth.password.PasswordEncoder
import com.lorenzoog.zipzop.controllers.Login
import com.lorenzoog.zipzop.dto.auth.LoginResponseDTO
import com.lorenzoog.zipzop.dto.user.UserCreateDTO
import com.lorenzoog.zipzop.dto.user.UserResponseDTO
import com.lorenzoog.zipzop.dto.user.toDto
import com.lorenzoog.zipzop.module
import com.lorenzoog.zipzop.services.user.UserService
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.*
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.modules
import kotlin.test.Test
import kotlin.test.assertEquals
import org.koin.dsl.module as koinModule

@KtorExperimentalAPI
class ApplicationTest {
  private object PasswordEncoderMock : PasswordEncoder {
    override fun matches(password: String, hashedPassword: String) = true

    override fun encode(password: String) = password
  }

  private fun testApp(block: TestApplicationEngine.() -> Unit) {
    withApplication(createTestEnvironment {
      config = HoconApplicationConfig(ConfigFactory.load("test-application"))

      module {
        module(testing = true)

        modules(koinModule {
          single<PasswordEncoder>(override = true) { PasswordEncoderMock }
        })
      }
    }) {
      block()
    }
  }

  @Test
  fun testRoot(): Unit = testApp {
    val userService by application.inject<UserService>()

    val username = "fake username"
    val password = "fake password"

    val user = runBlocking {
      userService.create(UserCreateDTO(
        username,
        password
      ))
    }

    lateinit var token: LoginResponseDTO

    handleRequest(HttpMethod.Post, "/login") {
      addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
      addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())

      setBody(Json.stringify(Login.Body.serializer(), Login.Body(
        username,
        password
      )))
    }.apply {
      assertEquals(HttpStatusCode.OK, response.status())

      token = Json.parse(LoginResponseDTO.serializer(), response.content.toString())
    }

    handleRequest(HttpMethod.Get, "/session") {
      addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
      addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
      addHeader(HttpHeaders.Authorization, token.jwt)
    }.apply {
      assertEquals(HttpStatusCode.OK, response.status())
      assertEquals(Json.stringify(UserResponseDTO.serializer(), user.toDto()), response.content)
    }
  }
}
