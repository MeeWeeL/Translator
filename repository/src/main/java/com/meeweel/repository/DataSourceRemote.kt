//package com.meeweel.repository
//
//import com.meeweel.translator.model.App
//import com.meeweel.model.DataModel
//import com.meeweel.repository.retrofit.RetrofitImpl
//import com.meeweel.repository.room.convertDataModelListToEntityList
//
//class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
//    DataSource<List<com.meeweel.model.DataModel>> {
//
//    val db = App.create().getEntityDao()
//
//    override suspend fun getData(word: String): List<com.meeweel.model.DataModel> {
//        db.getWordByText("week")
//        val a = remoteProvider
//            .getData(word).apply {
//                        db.insertList(convertDataModelListToEntityList(this))
//            }
//        return a
//    }
//
//    override suspend fun getData(): List<com.meeweel.model.DataModel> {
//        TODO("Not yet implemented")
//    }
////    override fun getData(word: String): Observable<List<DataModel>> {
////        db.getWordByText("week")
////        val a = remoteProvider
////            .getData(word).apply {
////                this.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation())
////                    .subscribe({
////                        db.insertList(convertDataModelListToEntityList(it))
////                    },{})
////            }
////        return a
////    }
//}