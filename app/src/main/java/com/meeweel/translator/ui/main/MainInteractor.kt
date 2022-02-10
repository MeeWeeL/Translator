package com.meeweel.translator.ui.main

import com.meeweel.translator.model.data.AppState
import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.repository.Repository
import com.meeweel.translator.presenter.Interactor
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}