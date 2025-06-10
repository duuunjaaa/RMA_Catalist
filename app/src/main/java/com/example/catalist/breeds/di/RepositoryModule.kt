package com.example.catalist.breeds.di

import com.example.catalist.breeds.repository.BreedRepositoryMock
import com.example.catalist.breeds.repository.BreedsRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBreedsRepository(
        impl: BreedRepositoryMock
    ): BreedsRepositoryInterface
}