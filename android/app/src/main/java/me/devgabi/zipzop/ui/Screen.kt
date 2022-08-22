package me.devgabi.zipzop.ui

enum class ScreenName(val sysName: String) {
  Home("screen"),
  DirectMessages("direct_messages"),
  Login("login"),
  Register("register")
}

sealed class Screen(val name: ScreenName) {
  object Home : Screen(ScreenName.Home)
  object Login : Screen(ScreenName.Login)
  object Register : Screen(ScreenName.Register)

  data class DirectMessages(val targetId: Long) : Screen(ScreenName.DirectMessages)

  companion object {
    fun of(name: ScreenName) = when (name) {
      ScreenName.Home -> Home
      ScreenName.Register -> Register
      ScreenName.Login -> Login
      ScreenName.DirectMessages -> TODO()
    }
  }
}
