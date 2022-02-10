package com.meeweel.translator.model.datasource

import io.reactivex.rxjava3.core.Observable


interface DataSource<T> {

    suspend fun getData(word: String): T
//    fun getData(word: String): Observable<T>
}