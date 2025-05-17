package com.dicoding.asclepius.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.prediction.PredictionLog

@Database(entities = [PredictionLog::class], version = 1)
abstract class AsclepiusRoomDatabase : RoomDatabase() {
    abstract fun predictionDao(): PredictionDao // Penamaan dengan camelCase

    companion object {
        @Volatile
        private var INSTANCE: AsclepiusRoomDatabase? = null // Perbaiki tipe data menjadi `AsclepiusRoomDatabase?`

        fun getDatabase(context: Context): AsclepiusRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsclepiusRoomDatabase::class.java, // Ganti `RoomDatabase::class` dengan `AsclepiusRoomDatabase::class`
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}