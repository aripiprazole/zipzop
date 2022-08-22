package me.devgabi.zipzop.auth.password

import de.mkammerer.argon2.Argon2Factory

class Argon2PasswordEncoder : PasswordEncoder {
  companion object {
    const val ITERATIONS = 22
    const val MEMORY = 64 * 1024
    const val PARALLELISM = 4
  }

  private val argon2 = Argon2Factory.create()

  override fun matches(password: String, hashedPassword: String): Boolean {
    return argon2.verify(password, hashedPassword.toByteArray())
  }

  override fun encode(password: String): String {
    return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toByteArray())
  }
}
