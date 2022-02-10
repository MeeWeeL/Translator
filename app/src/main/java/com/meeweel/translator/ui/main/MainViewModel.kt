package com.meeweel.translator.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.translator.model.data.AppState
import com.meeweel.translator.model.datasource.DataSourceLocal
import com.meeweel.translator.model.datasource.DataSourceRemote
import com.meeweel.translator.model.repository.RepositoryImpl
import com.meeweel.translator.rx.ISchedulerProvider
import com.meeweel.translator.rx.SchedulerProvider
import com.meeweel.translator.ui.base.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val interactor: MainInteractor
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val schedulerProvider: ISchedulerProvider = SchedulerProvider()

//    private val liveDataWordToSave: MutableLiveData<String> = MutableLiveData()
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

//    private val liveDataWord: LiveData<String> = liveDataWordToSave
    fun liveData(): LiveData<AppState> {
        return liveDataForViewToObserve
    }


    fun getData(word: String, isOnline: Boolean) {
//        liveDataWordToSave.value = word
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe{ doOnSubscribe() }
                .subscribeWith(getObserver())
        )
    }

//    fun reloadSavedState() {
//        val word = liveDataWord.value
//        if (word != null) getData(word, true)
//    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = AppState.Loading(null) }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            // Данные успешно загружены; сохраняем их и передаем во View (через
            // LiveData). View сама разберётся, как их отображать
            override fun onNext(state: AppState) {
                liveDataForViewToObserve.value = state
            }
            // В случае ошибки передаём её в Activity таким же образом через LiveData
            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}