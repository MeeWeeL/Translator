package com.meeweel.translator.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.meeweel.translator.di.DaggerAppComponent
import com.meeweel.translator.model.datasource.room.DBStorage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    @SuppressLint("StaticFieldLeak")
    object ContextHolder { lateinit var context: Context }

    override fun onCreate() {
        super.onCreate()
        ContextHolder.context = this
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    companion object DB {
        val db: DBStorage by lazy {
            Room.databaseBuilder(ContextHolder.context,
                DBStorage::class.java, "translatory.db").build()
        }

        fun create(): DBStorage = db
    }
}