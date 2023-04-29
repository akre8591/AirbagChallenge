package com.example.airbarchallenge.di

import com.example.airbarchallenge.data.TVShowsRepositoryImp
import com.example.airbarchallenge.domain.repositories.TVShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTvShowsRepository(
        tvShowsRepositoryImp: TVShowsRepositoryImp
    ): TVShowsRepository


}