package com.meeweel.historyscreen

interface HistoryInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
    suspend fun getData(): T
//    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}