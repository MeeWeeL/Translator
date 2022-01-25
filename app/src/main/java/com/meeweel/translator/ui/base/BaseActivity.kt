package com.meeweel.translator.ui.base


import androidx.appcompat.app.AppCompatActivity
import com.meeweel.translator.model.data.AppState

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)
}