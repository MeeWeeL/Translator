//package com.meeweel.core.base
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.meeweel.model.AppState
//import com.meeweel.translator.model.data.AppState
//import com.meeweel.translator.rx.SchedulerProvider
//import io.reactivex.rxjava3.disposables.CompositeDisposable
//
//abstract class BaseViewModel<T : AppState>(
//    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
//    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
//    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
//) : ViewModel() {
//
//    open fun getData(word: String, isOnline: Boolean) : LiveData<T> = liveDataForViewToObserve
//
//    override fun onCleared() {
//        compositeDisposable.clear()
//    }
//}