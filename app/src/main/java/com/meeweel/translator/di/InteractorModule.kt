package com.meeweel.translator.di

import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.DataSource
import com.meeweel.translator.model.datasource.DataSourceRemote
import com.meeweel.translator.model.datasource.retrofit.RetrofitImpl
import com.meeweel.translator.model.datasource.room.RoomDataBaseImpl
import com.meeweel.translator.model.repository.Repository
import com.meeweel.translator.model.repository.RepositoryImpl
import com.meeweel.translator.ui.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}