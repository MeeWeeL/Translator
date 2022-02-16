package com.meeweel.repository.room

import androidx.room.Entity

@Entity
data class MeaningsEntity(

    val translation: String = "",
    val imageUrl: String = ""
)