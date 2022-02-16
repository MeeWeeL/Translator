package com.meeweel.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class DBStorage : RoomDatabase() {

    abstract fun getEntityDao(): EntityDao

}