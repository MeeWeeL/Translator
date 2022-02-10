package com.meeweel.repository.retrofit

import com.meeweel.model.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<com.meeweel.model.DataModel>>
//    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}