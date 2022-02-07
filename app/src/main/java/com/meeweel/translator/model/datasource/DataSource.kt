package com.meeweel.translator.model.datasource

import io.reactivex.rxjava3.core.Observable


interface DataSource<T> {

    suspend fun getData(word: String): T
    suspend fun getData(): T
//    fun getData(word: String): Observable<T>
}