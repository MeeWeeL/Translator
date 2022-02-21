package com.meeweel.repository

import com.meeweel.model.DataModel
import com.meeweel.repository.room.RoomDataBaseImpl

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl) :
    DataSource<List<com.meeweel.model.DataModel>> {


    override suspend fun getData(word: String): List<com.meeweel.model.DataModel> =
        remoteProvider.getData(word)

    override suspend fun getData(): List<com.meeweel.model.DataModel> = remoteProvider.getData()
    override fun insertList(list: List<DataModel>) {
        remoteProvider.insertList(list)
    }
//    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}