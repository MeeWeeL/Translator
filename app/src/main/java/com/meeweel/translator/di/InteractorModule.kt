package com.meeweel.translator.di

import com.meeweel.model.DataModel
import com.meeweel.repository.Repository
import com.meeweel.translator.ui.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: com.meeweel.repository.Repository<List<com.meeweel.model.DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}