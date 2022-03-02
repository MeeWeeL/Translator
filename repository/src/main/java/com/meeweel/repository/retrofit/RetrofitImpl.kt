package com.meeweel.repository.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.meeweel.model.DataModel
import com.meeweel.repository.BaseInterceptor
import com.meeweel.repository.DataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return getService(BaseInterceptor.interceptor).search(word).await()
    }

    private fun getService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor).create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }

    override suspend fun getData(): List<DataModel> {
        TODO("Not yet implemented")
    }

    override fun insertList(list: List<DataModel>) {
        TODO("Not yet implemented")
    }
}