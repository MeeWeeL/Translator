package com.meeweel.repository.room

//import com.meeweel.translator.model.App
import com.meeweel.model.DataModel
import com.meeweel.repository.DataSource

class RoomDataBaseImpl(private val dbStorage: DBStorage) : DataSource<List<DataModel>> {

    private val db = dbStorage.getEntityDao()

    override suspend fun getData(word: String): List<DataModel> {
//    override fun getData(word: String): Observable<List<DataModel>> {
        return convertEntityListToDataModelList(db.getWordByText(word))
//        return db.getWordByText(word).flatMap {
//            Observable.just(convertEntityListToDataModelList(it))
//        }
    }
    override suspend fun getData(): List<DataModel> {
        return convertEntityListToDataModelList(db.getAll())
//        }
    }

    override fun insertList(list: List<DataModel>) {
        db.insertList(convertDataModelListToEntityList(list))
    }
}