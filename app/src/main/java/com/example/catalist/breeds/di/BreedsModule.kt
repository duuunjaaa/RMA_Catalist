package com.example.catalist.breeds.di

import com.example.catalist.breeds.api.BreedsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreedsModule {

    @Provides
    @Singleton
    fun provideBreedsApi(retrofit: Retrofit): BreedsApi = retrofit.create()
}
