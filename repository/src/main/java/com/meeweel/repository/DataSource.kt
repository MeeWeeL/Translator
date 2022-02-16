package com.meeweel.repository

import com.meeweel.model.DataModel

interface DataSource<T> {

    fun insertList(list: List<DataModel>)
    suspend fun getData(word: String): T
    suspend fun getData(): T
}