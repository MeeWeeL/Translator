package com.meeweel.repository

import com.meeweel.model.DataModel


interface Repository<T> {

    fun insertList(list: List<DataModel>)
    suspend fun getData(word: String): T
    suspend fun getData(): T
//    fun getData(word: String): Observable<T>
}