package com.solara.pofindr.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.solara.pofindr.data.dao.PostOfficeDao
import com.solara.pofindr.data.model.PostOffice

@Database(entities = [PostOffice::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postOfficeDa(): PostOfficeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "post_office_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}