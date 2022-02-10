package com.meeweel.historyscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.model.AppState
//import com.meeweel.translator.model.data.AppState
import kotlinx.coroutines.*

class HistoryViewModel(private val interactor: HistoryInteractorImpl) :
    ViewModel() {
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    private val _mutableLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData as LiveData<AppState>

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    fun getData() {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor() }
    }

    private suspend fun startInteractor() {
        _mutableLiveData.postValue(interactor.getData())
    }

    private fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)//Set View to original state in onStop
        super.onCleared()
    }
    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}
