package com.example.catalist.breeds.repository

import com.example.catalist.breeds.domain.Breed
//import com.example.catalist.breeds.network.BreedsApi
import javax.inject.Inject

class BreedsRepository : BreedsRepositoryInterface
{
    // TODO: dodati the CatAPI
    override suspend fun getAllBreeds(): List<Breed> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBreeds(query: String): List<Breed> {
        TODO("Not yet implemented")
    }

    override suspend fun getBreedById(breedId: String): Breed? {
        TODO("Not yet implemented")
    }
}
