package com.meeweel.translator.model.repository

import io.reactivex.rxjava3.core.Observable


interface Repository<T> {

    suspend fun getData(word: String): T
//    fun getData(word: String): Observable<T>
}