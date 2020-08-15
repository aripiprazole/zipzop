package com.lorenzoog.zipzop.ui.screens.loginscreen

import androidx.compose.Composable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.drawBackground
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp
import com.lorenzoog.zipzop.R

@Composable
fun LoginHeader() {
  val context = ContextAmbient.current

  val drawable = ContextCompat.getDrawable(context, R.drawable.app_logo)!!
  val bitmap = drawable.toBitmap(width = 150, height = 150)

  Column(
    modifier = Modifier
      .drawBackground(MaterialTheme.colors.primary)
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
