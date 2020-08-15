package com.lorenzoog.zipzop.ui

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import com.lorenzoog.zipzop.ui.screens.loginscreen.LoginScreen

@Composable
fun App() {
  val navigation = Navigation.current

  Crossfade(navigation.currentScreen) { screen ->
    when (screen) {
      Screen.Login, Screen.Home -> LoginScreen()

      else -> TODO()
    }
  }
}
