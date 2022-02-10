package com.meeweel.translator.ui.main

import com.meeweel.translator.di.NAME_LOCAL
import com.meeweel.translator.di.NAME_REMOTE
import com.meeweel.translator.model.data.AppState
import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.repository.Repository
import com.meeweel.translator.presenter.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Named

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