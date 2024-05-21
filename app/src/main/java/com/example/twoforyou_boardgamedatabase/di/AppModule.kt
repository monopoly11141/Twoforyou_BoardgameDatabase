package com.example.twoforyou_boardgamedatabase.di

import com.example.twoforyou_boardgamedatabase.data.repository.detail.DetailRepositoryImpl
import com.example.twoforyou_boardgamedatabase.data.repository.display.DisplayRepositoryImpl
import com.example.twoforyou_boardgamedatabase.domain.DetailRepository
import com.example.twoforyou_boardgamedatabase.domain.DisplayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDisplayBoardgameRepository() : DisplayRepository {
        return DisplayRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesAddBoardgameRepository(retrofit: Retrofit) : DetailRepository {
        return DetailRepositoryImpl(retrofit)
    }
}