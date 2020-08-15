package com.lorenzoog.zipzop.ui.screens.loginscreen

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.ColumnScope.gravity
import androidx.ui.layout.RowScope.weight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.tooling.preview.Preview

@Composable
fun LoginScreen() {
  VerticalScroller(
    modifier = Modifier
      .weight(10f)
      .fillMaxWidth()
      .gravity(Alignment.CenterHorizontally)
  ) {
    LoginHeader()
    LoginBody()
  }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  LoginScreen()
}
