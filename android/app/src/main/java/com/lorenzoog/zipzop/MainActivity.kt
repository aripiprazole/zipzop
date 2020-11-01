package com.lorenzoog.zipzop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.setContent
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.lorenzoog.zipzop.ui.App
import com.lorenzoog.zipzop.ui.ZipZopTheme

class MainActivity : AppCompatActivity() {
  private val backPressHandler = BackPressHandler()

  @ExperimentalFoundationApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ZipZopTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Providers(
            AmbientBackPressHandler provides backPressHandler
          ) {
            App()
          }
        }
      }
    }
  }

  override fun onBackPressed() {
    super.onBackPressed()

    backPressHandler.handle()
  }
}
