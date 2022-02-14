package com.meeweel.translator.di

import com.meeweel.model.DataModel
import com.meeweel.repository.DataSource
import com.meeweel.repository.retrofit.RetrofitImpl
import com.meeweel.repository.room.RoomDataBaseImpl
import com.meeweel.repository.Repository
import com.meeweel.repository.RepositoryImpl
import com.meeweel.repository.room.DBStorage
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
            Repository<List<DataModel>> =
        RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>):
            Repository<List<DataModel>> =
        RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(dbStorage: DBStorage): DataSource<List<DataModel>> =
        RoomDataBaseImpl(dbStorage)
}