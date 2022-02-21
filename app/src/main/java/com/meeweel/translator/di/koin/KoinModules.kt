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
import com.meeweel.translator.ui.main.MainActivity
import com.meeweel.translator.ui.main.MainInteractor
import com.meeweel.translator.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), DBStorage::class.java, "translatory.db").build() }
    single { get<DBStorage>().getEntityDao() }

    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImpl(
            RetrofitImpl()
        )
    }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImpl(
            RoomDataBaseImpl(get())
        )
    }
}
// Функция factory сообщает Koin, что эту зависимость нужно создавать каждый
// раз заново, что как раз подходит для Activity и её компонентов.
val mainScreen = module {
    scope(named("MainActivity")) {
        scoped { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
        viewModel { MainViewModel(get()) }
    }
//    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
//    factory { MainViewModel(get()) }
}

val historyScreen = module {
    scope(named("HistoryActivity")) {
        scoped { HistoryInteractorImpl(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
        viewModel { HistoryViewModel(get()) }
    }
//    factory { HistoryViewModel(get()) }
//    factory {
//        HistoryInteractorImpl(
//            get(named(NAME_REMOTE)),
//            get(named(NAME_LOCAL))
//        )
//    }
}