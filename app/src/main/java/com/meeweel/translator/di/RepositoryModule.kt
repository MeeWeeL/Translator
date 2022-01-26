package com.meeweel.translator.di

import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.datasource.DataSource
import com.meeweel.translator.model.datasource.retrofit.RetrofitImpl
import com.meeweel.translator.model.datasource.room.RoomDataBaseImpl
import com.meeweel.translator.model.repository.Repository
import com.meeweel.translator.model.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>):
            Repository<List<DataModel>> = RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>):
            Repository<List<DataModel>> = RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> = RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBaseImpl()
}