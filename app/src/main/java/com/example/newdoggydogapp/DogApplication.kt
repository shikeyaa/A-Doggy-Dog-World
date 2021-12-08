package com.example.newdoggydogapp

import android.app.Application
import com.example.newdoggydogapp.database.AppDatabase

class DogApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}