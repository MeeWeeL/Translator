package com.meeweel.translator.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.translator.model.App
import com.meeweel.model.AppState
import kotlinx.coroutines.*


class MainViewModel(
    private val interactor: MainInteractor
) : ViewModel() {
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun liveData(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    private fun handleError(throwable: Throwable) {
        Toast.makeText(App.ContextHolder.context, throwable.message, Toast.LENGTH_SHORT).show()
    }

    fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))

        }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}