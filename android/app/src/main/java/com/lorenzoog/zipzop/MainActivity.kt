package com.lorenzoog.zipzop

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Providers
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import com.lorenzoog.zipzop.ui.App
import com.lorenzoog.zipzop.ui.Navigation
import com.lorenzoog.zipzop.ui.NavigationViewModel
import com.lorenzoog.zipzop.ui.ZipZopTheme

class MainActivity : AppCompatActivity() {
  private val navigationViewModel by viewModels<NavigationViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ZipZopTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Providers(Navigation provides navigationViewModel) {
            App()
          }
        }
      }
    }
  }
}
