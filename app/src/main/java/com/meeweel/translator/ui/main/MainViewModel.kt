package com.meeweel.translator.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.translator.model.App
import com.meeweel.model.AppState
//import io.reactivex.rxjava3.disposables.Disposable
//import io.reactivex.rxjava3.observers.DisposableObserver
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


//    private val compositeDisposable = CompositeDisposable()
//    private val schedulerProvider: ISchedulerProvider = SchedulerProvider()



    fun getData(word: String, isOnline: Boolean) {
//        liveDataWordToSave.value = word
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
//        compositeDisposable.add(
//                interactor.getData(word, isOnline)
//                    .subscribeOn(schedulerProvider.io())
//                    .observeOn(schedulerProvider.ui())
//                    .doOnSubscribe{ doOnSubscribe() }
//                    .subscribeWith(getObserver())
//                )
    }

//    fun reloadSavedState() {
//        val word = liveDataWord.value
//        if (word != null) getData(word, true)
//    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        liveDataForViewToObserve.postValue(interactor.getData(word, isOnline))
//        liveDataForViewToObserve.postValue(parseSearchResults(interactor.getData(word, isOnline)))

    }
    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

//    private fun doOnSubscribe(): (Disposable) -> Unit =
//        { liveDataForViewToObserve.value = com.meeweel.model.AppState.Loading(null) }
//
//    private fun getObserver(): DisposableObserver<com.meeweel.model.AppState> {
//        return object : DisposableObserver<com.meeweel.model.AppState>() {
//            // Данные успешно загружены; сохраняем их и передаем во View (через
//            // LiveData). View сама разберётся, как их отображать
//            override fun onNext(state: com.meeweel.model.AppState) {
//                liveDataForViewToObserve.value = state
//            }
//            // В случае ошибки передаём её в Activity таким же образом через LiveData
//            override fun onError(e: Throwable) {
//                liveDataForViewToObserve.value = com.meeweel.model.AppState.Error(e)
//            }
//
//            override fun onComplete() {
//            }
//        }
//    }
}