package com.example.twoforyou_boardgamedatabase.di

import com.example.twoforyou_boardgamedatabase.data.db.local.BoardgameDao
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
    fun providesDisplayBoardgameRepository(
        retrofit: Retrofit,
        boardgameDao: BoardgameDao
    ) : DisplayRepository {
        return DisplayRepositoryImpl(retrofit, boardgameDao)
    }

    @Provides
    @Singleton
    fun providesDetailRepository(boardgameDao: BoardgameDao) : DetailRepository {
        return DetailRepositoryImpl(boardgameDao)
    }
}