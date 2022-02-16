package com.meeweel.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntityDao {

    @Query("SELECT * FROM Entity")
    fun getAll(): List<Entity>

    @Query("SELECT * FROM Entity WHERE text LIKE :text")
    fun getWordByText(text: String): List<Entity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(entities: List<Entity>)

}