//package com.meeweel.translator.ui.main
//
//import com.meeweel.model.AppState
//import com.meeweel.repository.DataSourceLocal
//import com.meeweel.repository.DataSourceRemote
//import com.meeweel.repository.RepositoryImpl
//import com.meeweel.translator.presenter.Presenter
//import com.meeweel.core.base.View
//import io.reactivex.rxjava3.disposables.CompositeDisposable
//import io.reactivex.rxjava3.observers.DisposableObserver
//
//class MainPresenterImpl<T : com.meeweel.model.AppState, V : com.meeweel.core.base.View>(
//    private val interactor: MainInteractor = MainInteractor(
//        com.meeweel.repository.RepositoryImpl(com.meeweel.repository.DataSourceRemote()),
//        com.meeweel.repository.RepositoryImpl(com.meeweel.repository.DataSourceLocal())
//    ),
//    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
////    // Мы можем обойтись и без SchedulerProvider, но он вам пригодится для тестирования приложения  -- мы будем рассматривать его на следующем курсе более подробно
////    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
//) : Presenter<T, V> {
//    // Ссылка на View, никакого контекста
//    private var currentView: V? = null
//    // Как только появилась View, сохраняем ссылку на неё для дальнейшей работы
//    override fun attachView(view: V) {
//        if (view != currentView) {
//            currentView = view
//        }
//    }
//    // View скоро будет уничтожена: прерываем все загрузки и обнуляем ссылку,
//    // чтобы предотвратить утечки памяти и NPE
//    override fun detachView(view: V) {
//        compositeDisposable.clear()
//        if (view == currentView) {
//            currentView = null
//        }
//    }
//    // Стандартный код RxJava
//    override fun getData(word: String, isOnline: Boolean) {
////        compositeDisposable.add(
////            interactor.getData(word, isOnline)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
//////                .subscribeOn(schedulerProvider.io)
//////                .observeOn(schedulerProvider.ui())
////                // Как только начинается загрузка, передаём во View модель данных для
////                // отображения экрана загрузки
////                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
////                .subscribeWith(getObserver())
////        )
//    }
//
//    private fun getObserver(): DisposableObserver<com.meeweel.model.AppState> {
//        return object : DisposableObserver<com.meeweel.model.AppState>() {
//
//            override fun onNext(appState: com.meeweel.model.AppState) {
//                // Если загрузка окончилась успешно, передаем модель с данными
//                // для отображения
//                currentView?.renderData(appState)
//            }
//
//            override fun onError(e: Throwable) {
//                // Если произошла ошибка, передаем модель с ошибкой
//                currentView?.renderData(com.meeweel.model.AppState.Error(e))
//            }
//
//            override fun onComplete() {
//            }
//        }
//    }
//}