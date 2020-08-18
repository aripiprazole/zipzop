package com.lorenzoog.zipzop.ui.screens.loginscreen

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.foundation.layout.RowScope.weight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen() {
  ScrollableColumn(
    modifier = Modifier
      .weight(10f)
      .fillMaxWidth()
      .gravity(Alignment.CenterHorizontally)
  ) {
    LoginHeader()
    LoginBody()
  }
}
