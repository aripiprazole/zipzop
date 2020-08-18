package com.lorenzoog.zipzop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
//import com.lorenzoog.zipzop.database.AppDatabase
//import com.lorenzoog.zipzop.database.AppDatabaseAmbient
import com.lorenzoog.zipzop.ui.App
import com.lorenzoog.zipzop.ui.ZipZopTheme

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ZipZopTheme {
        // A surface container using the 'background' color from the theme
        Box(backgroundColor = MaterialTheme.colors.background) {
//          Providers(AppDatabaseAmbient provides AppDatabase.getDatabase(applicationContext)) {
          App()
//          }
        }
      }
    }
  }
}
