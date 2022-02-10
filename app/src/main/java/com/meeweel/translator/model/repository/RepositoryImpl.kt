package com.meeweel.translator.model.repository

import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}