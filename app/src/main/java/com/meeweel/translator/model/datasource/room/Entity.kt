package com.meeweel.translator.model.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Entity(
    @PrimaryKey(autoGenerate = false)
    val text: String,
    val meanings: String
)