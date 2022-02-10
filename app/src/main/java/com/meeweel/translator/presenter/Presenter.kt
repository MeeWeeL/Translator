package com.meeweel.translator.presenter


interface Presenter<T : com.meeweel.model.AppState, V : com.meeweel.core.base.View> {

    fun attachView(view: V)

    fun detachView(view: V)
    // Получение данных с флагом isOnline(из Интернета или нет)
    fun getData(word: String, isOnline: Boolean)
}