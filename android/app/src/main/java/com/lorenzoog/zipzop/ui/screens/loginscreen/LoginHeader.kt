package com.lorenzoog.zipzop.ui.screens.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.lorenzoog.zipzop.R

@Composable
fun LoginHeader() {
  val context = ContextAmbient.current

  val drawable = ContextCompat.getDrawable(context, R.drawable.app_logo)!!
  val bitmap = drawable.toBitmap(width = 150, height = 150)

  Column(
    modifier = Modifier
      .background(MaterialTheme.colors.primary)
      .fillMaxWidth()
      .heightIn(
        minHeight = 200.dp,
        maxHeight = 280.dp
      ),
    verticalArrangement = Arrangement.Center
  ) {
    Image(
      modifier = Modifier
        .gravity(Alignment.CenterHorizontally),
      asset = bitmap.asImageAsset()
    )
  }

  Spacer(modifier = Modifier.padding(bottom = 22.dp))
}
