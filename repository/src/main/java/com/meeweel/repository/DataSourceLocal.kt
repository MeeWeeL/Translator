package com.meeweel.repository

import com.meeweel.model.DataModel
import com.meeweel.repository.room.RoomDataBaseImpl

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> =
        remoteProvider.getData(word)

    override suspend fun getData(): List<DataModel> = remoteProvider.getData()
    override fun insertList(list: List<DataModel>) {
        remoteProvider.insertList(list)
    }
}