package com.meeweel.translator.model.datasource

import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.room.RoomDataBaseImpl
import io.reactivex.rxjava3.core.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}