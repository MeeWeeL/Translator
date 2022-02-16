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
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: com.meeweel.repository.DataSource<List<com.meeweel.model.DataModel>>):
            com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>> =
        com.meeweel.repository.RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: com.meeweel.repository.DataSource<List<com.meeweel.model.DataModel>>):
            com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>> =
        com.meeweel.repository.RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): com.meeweel.repository.DataSource<List<com.meeweel.model.DataModel>> =
        com.meeweel.repository.retrofit.RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(dbStorage: DBStorage): com.meeweel.repository.DataSource<List<com.meeweel.model.DataModel>> =
        com.meeweel.repository.room.RoomDataBaseImpl(dbStorage)
}