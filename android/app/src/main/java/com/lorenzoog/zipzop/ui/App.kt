package com.lorenzoog.zipzop.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.github.zsoltk.compose.router.Router
import com.lorenzoog.zipzop.ui.screens.loginscreen.LoginScreen

@ExperimentalFoundationApi
@Composable
fun App(screen: Screen = Screen.Home) {
  Router(screen) { backStack ->
    when(val routing = backStack.last()) {
      is Screen.Home -> LoginScreen()
    }
  }
}
