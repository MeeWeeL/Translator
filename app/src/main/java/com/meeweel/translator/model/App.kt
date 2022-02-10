package com.meeweel.translator.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.meeweel.translator.model.datasource.room.DBStorage

class App : Application() {

    @SuppressLint("StaticFieldLeak")
    object ContextHolder { lateinit var context: Context }

    override fun onCreate() {
        super.onCreate()
        ContextHolder.context = this
    }

    companion object DB {
        val db: DBStorage by lazy {
            Room.databaseBuilder(ContextHolder.context,
                DBStorage::class.java, "translatory.db").build()
        }

        fun create(): DBStorage = db
    }
}