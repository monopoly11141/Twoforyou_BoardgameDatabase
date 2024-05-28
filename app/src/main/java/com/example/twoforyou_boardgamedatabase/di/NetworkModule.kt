package com.example.twoforyou_boardgamedatabase.di

import com.example.twoforyou_boardgamedatabase.utils.Constant.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitInstance(
    ): Retrofit {
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .build()
    }

}