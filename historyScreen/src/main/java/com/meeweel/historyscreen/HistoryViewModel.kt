package com.meeweel.historyscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.model.AppState
import com.meeweel.model.DataModel
import com.meeweel.repository.Repository
import kotlinx.coroutines.*

class HistoryViewModel(
    private val repo: Repository<List<DataModel>>
) : ViewModel() {
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun getData() {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    private suspend fun startInteractor() {
        liveDataForViewToObserve.postValue(AppState.Success(repo.getData()))
    }

    private fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)//Set View to original state in onStop
        super.onCleared()
    }
    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}
