package com.meeweel.translator.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
//import com.meeweel.translator.di.DaggerAppComponent
import com.meeweel.translator.di.koin.application
import com.meeweel.translator.di.koin.historyScreen
import com.meeweel.translator.di.koin.mainScreen
import com.meeweel.repository.room.DBStorage
import org.koin.android.ext.koin.androidContext
//import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.startKoin

class App : Application() {

    @SuppressLint("StaticFieldLeak")
    object ContextHolder { lateinit var context: Context }

    override fun onCreate() {
        super.onCreate()
        ContextHolder.context = this

        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }

    }

//    companion object DB {
//        val db: com.meeweel.repository.room.DBStorage by lazy {
//            Room.databaseBuilder(ContextHolder.context,
//                com.meeweel.repository.room.DBStorage::class.java, "translatory.db").build()
//        }
//
//        fun create(): com.meeweel.repository.room.DBStorage = db
//    }
}