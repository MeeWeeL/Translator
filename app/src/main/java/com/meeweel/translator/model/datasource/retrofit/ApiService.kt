package com.meeweel.translator.model.datasource.retrofit

import com.meeweel.translator.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
//    fun search(@Query("search") wordToSearch: String): Observable<List<DataModel>>
}