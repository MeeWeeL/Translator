package com.meeweel.translator.presenter

import com.meeweel.translator.model.data.AppState
import com.meeweel.translator.ui.base.View

interface Presenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)
    // Получение данных с флагом isOnline(из Интернета или нет)
    fun getData(word: String, isOnline: Boolean)
}