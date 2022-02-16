package com.meeweel.historyscreen

import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import com.meeweel.repository.Repository
//import com.meeweel.translator.model.data.AppState
//import com.meeweel.translator.model.data.DataModel
//import com.meeweel.translator.model.repository.Repository
//import com.meeweel.translator.presenter.HistoryInteractor

class HistoryInteractorImpl(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: Repository<List<DataModel>>
) : HistoryInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }

    override suspend fun getData(): AppState {
        return AppState.Success(repositoryLocal.getData())
    }
}
