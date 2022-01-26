package com.meeweel.translator.model.datasource.room

import com.meeweel.translator.model.App
import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.DataSource
import io.reactivex.rxjava3.core.Observable

class RoomDataBaseImpl : DataSource<List<DataModel>> {

    private val db = App.create().getEntityDao()

    override fun getData(word: String): Observable<List<DataModel>> {
        return db.getWordByText(word).flatMap {
            Observable.just(convertEntityListToDataModelList(it))
        }
    }
}