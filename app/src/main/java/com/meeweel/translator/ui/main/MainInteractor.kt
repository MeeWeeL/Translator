package com.meeweel.translator.ui.main

import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import com.meeweel.repository.Repository
import com.meeweel.core.base.Interactor

//class MainInteractor @Inject constructor(
//    @Named(NAME_REMOTE)private val remoteRepository: Repository<List<DataModel>>,
//    @Named(NAME_LOCAL)private val localRepository: Repository<List<DataModel>>
//) : Interactor<AppState> {

    class MainInteractor(
        private val remoteRepository: com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>>,
        private val localRepository: com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>>
    ) : com.meeweel.core.base.Interactor<com.meeweel.model.AppState> {

        override suspend fun getData(word: String, fromRemoteSource: Boolean): com.meeweel.model.AppState {
            return com.meeweel.model.AppState.Success(
                if (fromRemoteSource) {
                    remoteRepository
                } else {
                    localRepository
                }.getData(word)
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