package com.meeweel.translator.model.datasource

import com.meeweel.translator.model.App
import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.retrofit.RetrofitImpl
import com.meeweel.translator.model.datasource.room.convertDataModelListToEntityList
import com.meeweel.translator.model.datasource.room.convertDataModelToEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<List<DataModel>> {

    val db = App.create().getEntityDao()

    override fun getData(word: String): Observable<List<DataModel>> {
        db.getWordByText("week")
        val a = remoteProvider
            .getData(word).apply {
                this.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
                    .subscribe({
                        db.insertList(convertDataModelListToEntityList(it))
                    },{})
            }
        return a
    }
}