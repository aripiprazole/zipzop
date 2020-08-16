package com.lorenzoog.zipzop.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity
data class Contact(
  @PrimaryKey val id: Long
)

@Database(
  entities = [Contact::class],
  version = 1
)
abstract class AppDatabase : RoomDatabase()
