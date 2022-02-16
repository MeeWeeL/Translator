package com.meeweel.core.base

import com.meeweel.model.AppState

interface View {
    fun renderData(appState: AppState)

}