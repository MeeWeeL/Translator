package com.meeweel.core.base


interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}