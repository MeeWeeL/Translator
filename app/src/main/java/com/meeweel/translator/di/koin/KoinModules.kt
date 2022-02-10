package com.meeweel.translator.di.koin

import androidx.room.Room
import com.meeweel.translator.di.NAME_LOCAL
import com.meeweel.translator.di.NAME_REMOTE
import com.meeweel.model.DataModel
import com.meeweel.repository.retrofit.RetrofitImpl
import com.meeweel.repository.room.RoomDataBaseImpl
import com.meeweel.repository.Repository
import com.meeweel.repository.RepositoryImpl
import com.meeweel.historyscreen.HistoryInteractorImpl
import com.meeweel.historyscreen.HistoryViewModel
import com.meeweel.repository.room.DBStorage
import com.meeweel.translator.ui.main.MainInteractor
import com.meeweel.translator.ui.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), DBStorage::class.java, "HistoryDB").build() }
//    single { get<DBStorage>().historyDao() }
    // Функция single сообщает Koin, что эта зависимость должна храниться
    // в виде синглтона (в Dagger есть похожая аннотация)
    // Аннотация named выполняет аналогичную Dagger функцию
    single<com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>>>(named(NAME_REMOTE)) {
        com.meeweel.repository.RepositoryImpl(
            com.meeweel.repository.retrofit.RetrofitImpl()
        )
    }
    single<com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>>>(named(NAME_LOCAL)) {
        com.meeweel.repository.RepositoryImpl(
            com.meeweel.repository.room.RoomDataBaseImpl(get())
        )
    }
}
// Функция factory сообщает Koin, что эту зависимость нужно создавать каждый
// раз заново, что как раз подходит для Activity и её компонентов.
val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { com.meeweel.historyscreen.HistoryViewModel(get()) }
    factory {
        com.meeweel.historyscreen.HistoryInteractorImpl(
            get(named(NAME_REMOTE)),
            get(named(NAME_LOCAL))
        )
    }
}