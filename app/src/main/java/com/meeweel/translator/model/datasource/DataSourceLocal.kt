package com.meeweel.translator.model.datasource

import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.room.RoomDataBaseImpl
import io.reactivex.rxjava3.core.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
    override suspend fun getData(): List<DataModel> = remoteProvider.getData()
//    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}