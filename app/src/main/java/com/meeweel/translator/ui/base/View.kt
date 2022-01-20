package com.meeweel.translator.ui.base

import com.meeweel.translator.model.data.AppState

interface View {
    fun renderData(appState: AppState)

}