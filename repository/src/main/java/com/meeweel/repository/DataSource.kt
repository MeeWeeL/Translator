package com.meeweel.repository


interface DataSource<T> {

    suspend fun getData(word: String): T
    suspend fun getData(): T
//    fun getData(word: String): Observable<T>
}