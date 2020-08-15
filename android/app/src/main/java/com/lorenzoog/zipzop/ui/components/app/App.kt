package com.lorenzoog.zipzop.ui.components.app

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import com.lorenzoog.zipzop.ui.Navigation
import com.lorenzoog.zipzop.ui.Screen
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
