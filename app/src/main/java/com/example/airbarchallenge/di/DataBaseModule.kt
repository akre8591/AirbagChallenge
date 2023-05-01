package com.example.airbarchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.airbarchallenge.data.db.TvShowsDataBase
import com.example.airbarchallenge.utils.Constants.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        TvShowsDataBase::class.java,
        DATA_BASE_NAME
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideShowsDao(dataBase: TvShowsDataBase) = dataBase.showsDao()

}