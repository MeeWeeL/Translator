package com.meeweel.core.base

import com.meeweel.model.AppState
//import com.meeweel.translator.model.data.AppState

interface View {
    fun renderData(appState: AppState)

}