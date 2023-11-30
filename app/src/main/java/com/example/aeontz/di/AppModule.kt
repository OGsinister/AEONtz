package com.example.aeontz.di

import com.example.aeontz.domain.ApiRepository
import com.example.aeontz.Constants
import com.example.aeontz.data.ApiRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): ApiRepository = Retrofit.Builder()
        .baseUrl(Constants.BASE_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRepository::class.java)

    @Provides
    @Singleton
    fun provideApiRepository(apiRepository: ApiRepository): ApiRepositoryImpl {
        return ApiRepositoryImpl(apiRepository)
    }
}