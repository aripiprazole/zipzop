package com.lorenzoog.zipzop.database

import android.content.Context
import androidx.compose.ambientOf
import androidx.room.*

@Entity
data class Contact(
  @PrimaryKey val id: Long
)

val AppDatabaseAmbient = ambientOf<AppDatabase>()

@Database(
  entities = [Contact::class],
  version = 1
)
abstract class AppDatabase : RoomDatabase() {
  companion object {
    private val databaseClass = AppDatabase::class.java
    private const val databaseName = "app.db"

    fun getDatabase(context: Context) = Room.databaseBuilder(
      context,
      databaseClass,
      databaseName
    ).build()
  }
}
