package com.meeweel.translator.di.koin

import com.meeweel.translator.di.NAME_LOCAL
import com.meeweel.translator.di.NAME_REMOTE
import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.retrofit.RetrofitImpl
import com.meeweel.translator.model.datasource.room.RoomDataBaseImpl
import com.meeweel.translator.model.repository.Repository
import com.meeweel.translator.model.repository.RepositoryImpl
import com.meeweel.translator.ui.main.MainInteractor
import com.meeweel.translator.ui.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    // Функция single сообщает Koin, что эта зависимость должна храниться
    // в виде синглтона (в Dagger есть похожая аннотация)
    // Аннотация named выполняет аналогичную Dagger функцию
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl()) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImpl(RoomDataBaseImpl()) }
}
// Функция factory сообщает Koin, что эту зависимость нужно создавать каждый
// раз заново, что как раз подходит для Activity и её компонентов.
val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}