package com.example.catalist.breeds.repository

import com.example.catalist.breeds.domain.Breed

interface BreedsRepositoryInterface {
    suspend fun getAllBreeds(): List<Breed>
    suspend fun searchBreeds(query: String): List<Breed> // todo: nisam sigurna sta sam htela s ovom fjom
    suspend fun getBreedById(breedId: String): Breed?

}