package com.lorenzoog.zipzop.ui

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.ambientOf
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lorenzoog.zipzop.utils.getMutableStateOf

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

private const val SIS_NAME = "screen_name"

fun Bundle.toScreen(): Screen {
  val screenName = ScreenName.valueOf(requireNotNull(getString(SIS_NAME)) {
    "Missing $SIS_NAME in $this"
  })

  return Screen.of(screenName)
}

fun Screen.toBundle() = bundleOf(SIS_NAME to name.sysName)

val Navigation = ambientOf<NavigationViewModel> { error("Nothing set for navigation view model.") }

class NavigationViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
  var currentScreen by savedStateHandle.getMutableStateOf<Screen>(
    key = SIS_NAME,
    default = Screen.Home,
    save = { it.toBundle() },
    restore = { it.toScreen() }
  )
    private set

  @MainThread
  fun back(): Boolean {
    val wasHandled = currentScreen != Screen.Home

    currentScreen = Screen.Home

    return wasHandled
  }

  @MainThread
  fun navigateTo(screen: Screen) {
    currentScreen = screen
  }


}


