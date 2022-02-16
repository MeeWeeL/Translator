package com.meeweel.repository


interface Repository<T> {

    suspend fun getData(word: String): T
    suspend fun getData(): T
//    fun getData(word: String): Observable<T>
}