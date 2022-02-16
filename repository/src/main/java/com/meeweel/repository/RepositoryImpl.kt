package com.meeweel.repository

import com.meeweel.model.DataModel

class RepositoryImpl(private val dataSource: com.meeweel.repository.DataSource<List<com.meeweel.model.DataModel>>) :
    Repository<List<com.meeweel.model.DataModel>> {
    override suspend fun getData(word: String): List<com.meeweel.model.DataModel> {
//    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }

    override suspend fun getData(): List<com.meeweel.model.DataModel> {
        return dataSource.getData()
    }
}