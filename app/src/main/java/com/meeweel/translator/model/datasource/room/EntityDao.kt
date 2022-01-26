package com.meeweel.translator.model.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable

@Dao
interface EntityDao {

    @Query("SELECT * FROM Entity")
    fun getAll(): Observable<List<Entity>>

    @Query("SELECT * FROM Entity WHERE text LIKE :text")
    fun getWordByText(text: String): Observable<List<Entity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(entities: List<Entity>)

}