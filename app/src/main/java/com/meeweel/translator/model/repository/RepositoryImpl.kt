package com.meeweel.translator.model.repository

import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.DataSource
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
//    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }

    override suspend fun getData(): List<DataModel> {
        return dataSource.getData()
    }
}