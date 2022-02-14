package com.meeweel.translator.ui.main

import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import com.meeweel.repository.Repository
import com.meeweel.core.base.Interactor
import com.meeweel.repository.room.convertDataModelListToEntityList

//class MainInteractor @Inject constructor(
//    @Named(NAME_REMOTE)private val remoteRepository: Repository<List<DataModel>>,
//    @Named(NAME_LOCAL)private val localRepository: Repository<List<DataModel>>
//) : Interactor<AppState> {

    class MainInteractor(
        private val remoteRepository: Repository<List<DataModel>>,
        private val localRepository: Repository<List<DataModel>>
    ) : Interactor<AppState> {

        override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
            return AppState.Success(
                if (fromRemoteSource) {
                    remoteRepository
                } else {
                    localRepository
                }.getData(word).apply {
                    if (fromRemoteSource) localRepository.insertList(this)
                }
            )
        }
//        override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
//            return if (fromRemoteSource) {
//                remoteRepository
//            } else {
//                localRepository
//            }.getData(word).map { AppState.Success(it) }
//        }
}