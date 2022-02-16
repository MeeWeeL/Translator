package com.meeweel.repository

import com.meeweel.model.DataModel

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun getData(): List<DataModel> {
        return dataSource.getData()
    }

    override fun insertList(list: List<DataModel>) {
        dataSource.insertList(list)
    }
}