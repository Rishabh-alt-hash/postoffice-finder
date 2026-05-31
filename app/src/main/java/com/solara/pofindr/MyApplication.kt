package com.solara.pofindr

import android.app.Application
import com.solara.pofindr.data.db.AppDatabase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
    }
}