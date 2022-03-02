package com.meeweel.repository.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(
    @PrimaryKey(autoGenerate = false)
    val text: String,
    val meanings: String
)