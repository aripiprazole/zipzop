package com.lorenzoog.zipzop.ui.components.app

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import com.lorenzoog.zipzop.ui.Navigation
import com.lorenzoog.zipzop.ui.Screen

@Composable
fun App() {
  val navigation = Navigation.current

  Crossfade(navigation.currentScreen) { screen ->
    when (screen) {
      Screen.Home -> Column {
        Text("Hi there, this is the home screen")
      }

      else -> TODO()
    }
  }
}
